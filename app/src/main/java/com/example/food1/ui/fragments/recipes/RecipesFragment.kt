package com.example.food1.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food1.R
import com.example.food1.adapter.RecipesAdapter
import com.example.food1.databinding.FragmentRecipesBinding
import com.example.food1.util.NetworkListener
import com.example.food1.util.NetworkResult
import com.example.food1.util.observeOnce
import com.example.food1.viewmodels.MainViewModel
import com.example.food1.viewmodels.RecipesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
//
class recipesFragment : Fragment() , SearchView.OnQueryTextListener{

    private val args by navArgs<recipesFragmentArgs>()

    private var _binding:FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    private val mAdapter by lazy { RecipesAdapter() } //  it will only be initialized the first time it's accessed, and subsequent accesses will reuse the already created instance.

//    @OptIn(ExperimentalCoroutinesApi::class)
    private lateinit var networkListener: NetworkListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]

    }

    //    @SuppressLint("CutPasteId")
//    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

//        shimmerFrameLayout = binding.findViewById(R.id.shimmerFrameLayout)
//        recyclerView = mView.findViewById(R.id.recyclerview)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater){
                // Add items menu here
                menuInflater.inflate(R.menu.recipes_menu,menu)

                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@recipesFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setUpRecyclerView()

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner) {
            recipesViewModel.backOnline = it
        }

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect{ status ->
                    Log.d("NetworkListener",status.toString())
                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        binding.recipesFab.setOnClickListener{
            if(recipesViewModel.networkStatus){
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            }else{
                recipesViewModel.showNetworkStatus()
            }
        }
        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun setUpRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        shoShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData(){
        mainViewModel.getRecipe(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is NetworkResult.Success ->{
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error ->{
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading ->{
                    shoShimmerEffect()
                }
                else -> {

                }
            }
        }
    }

    private fun searchApiData(searchQuery:String){
        shoShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val foodRecipe = response.data
                    foodRecipe?.let { mAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    shoShimmerEffect()
                }

            }
        }
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner){database ->
                if (database.isNotEmpty()){
                    mAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }

    private fun shoShimmerEffect(){
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout.startShimmer()
        binding.recyclerview.visibility = View.GONE
    }

    private fun hideShimmerEffect(){
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
    }

    override fun onDestroy() { // with this wew will avoid memory leak
        super.onDestroy()
        _binding = null
    }


}