package com.example.food1.ui.fragments.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.food1.databinding.FragmentInstructionsBinding
import com.example.food1.models.Result
import com.example.food1.util.Constants
import com.example.food1.util.retrieveParcelable


class InstructionsFragment : Fragment() {
    private var _binding: FragmentInstructionsBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result = args!!.retrieveParcelable<Result>(Constants.RECIPE_RESULT_KEY) as Result

        binding.instructionsWebView.webViewClient = object : WebViewClient(){}
        val websiteUrl: String = myBundle.sourceUrl
        binding.instructionsWebView.loadUrl(websiteUrl)

        return binding.root
    }

}