package com.example.food1.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food1.R
import com.example.food1.adapter.FavoriteRecipesAdapter
import com.example.food1.databinding.FragmentFavoriteRecipesBinding
import com.example.food1.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [favoriteRecipesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class favoriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel:MainViewModel by viewModels()
    private val mAdapter:FavoriteRecipesAdapter by lazy{FavoriteRecipesAdapter(requireActivity(),mainViewModel)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentFavoriteRecipesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter
        setUpRecyclerView(binding.favoriteRecipesRecyclerView)
        setHasOptionsMenu(true)

        return binding.root
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "inflater.inflate(R.menu.favorite_recipes_menu, menu)",
        "com.example.food1.R"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu,menu)
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onOptionsItemSelected(item)",
        "androidx.fragment.app.Fragment"
    )
    )
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteAll_favorite_recipes_menu){
            mainViewModel.deleteAllFavoriteRecipes()
            showSnackBar()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar(){
        Snackbar.make(
            binding.root,
            "All recipes deleted",
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView){
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mAdapter.clearContextualActionMode()
    }
}