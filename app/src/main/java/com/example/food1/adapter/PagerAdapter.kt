package com.example.food1.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    private val resultBundle:Bundle, // use to pass data from recipes fragmnet to details activity
    private val fragments: ArrayList<Fragment>,
    fragmentActivity: FragmentActivity
) :FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        fragments[position].arguments = resultBundle
        return fragments[position]
    }
}