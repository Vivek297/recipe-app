package com.example.food1.util

import androidx.recyclerview.widget.DiffUtil

class RecipesDiffUtil<T>(
    private val oldList:List<T>,
    private val newList:List<T>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int { // returns size of the old list
        return oldList.size
    }

    override fun getNewListSize(): Int { // returns the size of the new list
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // called by DiffUtil to decide whether two object represent the same item in the old and new list
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Checks whether two items have the same data
        // you can change its behaviour depending on your ui
        // This method is called by DiffUtil only if
        // areItemTheSame returns true.
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}