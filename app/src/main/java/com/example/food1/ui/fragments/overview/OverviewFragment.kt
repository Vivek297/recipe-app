package com.example.food1.ui.fragments.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.example.food1.R
import com.example.food1.databinding.FragmentOverviewBinding
import com.example.food1.models.Result
import com.example.food1.util.Constants.Companion.RECIPE_RESULT_KEY
import com.example.food1.util.retrieveParcelable
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//    val view=  inflater.inflate(R.layout.fragment_overview, container, false)
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
//

        val args = arguments
        val myBundle: Result = args!!.retrieveParcelable<Result>(RECIPE_RESULT_KEY) as Result

        binding.mainImageView.load(myBundle.image)
        binding.timeTextView.text = myBundle.title
        binding.likesTextView.text = myBundle.aggregateLikes.toString()
        binding.timeTextView.text = myBundle.readyInMinutes.toString()
        binding.summaryTextView.text = myBundle.summary
        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            binding.summaryTextView.text = summary
        }

    if(myBundle.vegetarian == true){
                binding.vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if(myBundle.vegan == true){
                binding.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if(myBundle.glutenFree == true){
                binding.glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if(myBundle.dairyFree == true){
                binding.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if(myBundle.veryHealthy == true){
                binding.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if(myBundle.cheap == true){
                binding.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }



//        val myBundle: Result? = try {
//            args?.getParcelable("recipeBundle", Result::class.java)
//        }catch (e: ClassCastException){
//            null
//        }
//    val myBundle:Result? = try {
//        args?.getParcelable("recipeBundle", Result::class.java)
//    }catch (e: ClassCastException){
//        null
//    }
//        if (myBundle != null) {
//            binding.mainImageView.load(myBundle.image)
//            binding.timeTextView.text = myBundle.title
//            binding.likesTextView.text = myBundle.aggregateLikes.toString()
//            binding.timeTextView.text = myBundle.readyInMinutes.toString()
//            binding.summaryTextView.text = myBundle.summary
//
//            if(myBundle.vegetarian == true){
//                binding.vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
//                binding.vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
//            }
//            if(myBundle.vegan == true){
//                binding.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
//                binding.veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
//            }
//            if(myBundle.glutenFree == true){
//                binding.glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
//                binding.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
//            }
//            if(myBundle.dairyFree == true){
//                binding.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
//                binding.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
//            }
//            if(myBundle.veryHealthy == true){
//                binding.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
//                binding.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
//            }
//            if(myBundle.cheap == true){
//                binding.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
//                binding.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
//            }
//        }


//        if (myBundle != null) {
//            RecipesRowBinding.parseHtml(binding.summaryTextView, myBundle.summary)
//        }
//        myBundle?.summary.let {
//            val summary = Jsoup.parse(it).text()
//            binding.summaryTextView.text = summary
//        }



    return binding.root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}