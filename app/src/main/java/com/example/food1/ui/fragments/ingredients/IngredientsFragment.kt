package com.example.food1.ui.fragments.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food1.adapter.IngredientsAdapter
import com.example.food1.databinding.FragmentIngredientsBinding
import com.example.food1.models.Result
import com.example.food1.util.Constants
import com.example.food1.util.retrieveParcelable


class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private var _binding:FragmentIngredientsBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result = args!!.retrieveParcelable<Result>(Constants.RECIPE_RESULT_KEY) as Result

        setUpRecyclerView(binding)
        myBundle.extendedIngredients.let { mAdapter.setData(it) }

        return binding.root
    }

    private fun setUpRecyclerView(binding: FragmentIngredientsBinding){
        binding.ingredientsRecyclerview.adapter = mAdapter
        binding.ingredientsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }


}