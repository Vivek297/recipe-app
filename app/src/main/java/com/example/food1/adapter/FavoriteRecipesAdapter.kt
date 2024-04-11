package com.example.food1.adapter

import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.food1.R
import com.example.food1.data.database.entities.FavoritesEntity
import com.example.food1.databinding.FavoriteRecipesRowLayoutBinding
import com.example.food1.ui.fragments.favorites.favoriteRecipesFragmentDirections
import com.example.food1.util.RecipesDiffUtil
import com.example.food1.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteRecipesAdapter(
    private val requireActivity:FragmentActivity,
    private val mainViewModel: MainViewModel
): RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(),ActionMode.Callback {
    private lateinit var rootView:View
    private var multiSelection = false
    private var selectedRecipes =  arrayListOf<FavoritesEntity>()

    private var favoriteRecipes = emptyList<FavoritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()

    private lateinit var mActionMode: ActionMode
    class MyViewHolder(val binding:FavoriteRecipesRowLayoutBinding)
        :RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity){
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView
        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)
        /**
         * Single click Listener
         */
        holder.binding.favoriteRecipesRowLayout.setOnClickListener{
            if(multiSelection){
                applySelection(holder,currentRecipe)
            }else{
                val action = favoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                    currentRecipe.result
                )
                holder.itemView.findNavController().navigate(action)
            }
        }
        /**
         * Long click Listener
         */
        holder.binding.favoriteRecipesRowLayout.setOnLongClickListener {
            if(!multiSelection){
                multiSelection = true
                requireActivity.startActionMode(this )
                applySelection(holder,currentRecipe)
                true
            }else{
                multiSelection = false
                false
            }

        }
    }

    private fun applySelection(holder:MyViewHolder, currentRecipe:FavoritesEntity){
        if (selectedRecipes.contains(currentRecipe)){
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)
            applyActionModeTitle()
        }else{
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundLightColor,R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder:MyViewHolder, backgroundColor:Int, strokeColor:Int){
        holder.binding.favoriteRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity,backgroundColor)
        )
        holder.binding.favoriteRowCardview.strokeColor =
            ContextCompat.getColor(requireActivity,strokeColor)
    }

    private fun applyActionModeTitle(){
        when(selectedRecipes.size){
            0 -> {
                mActionMode.finish()
            }
            1 ->{
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else ->{
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    fun setData(newFavoriteRecipes:List<FavoritesEntity>){
        val favoriteRecipesDiffUtil =
            RecipesDiffUtil(favoriteRecipes,newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu,menu)
        mActionMode = actionMode!!
        return true

    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if(menu?.itemId == R.id.delete_favorite_recipe_menu){
            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipe(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed")

            multiSelection = false
            selectedRecipes.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)
        }

        multiSelection = false
        selectedRecipes.clear()
    }
    private fun showSnackBar(message:String){
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }
    fun clearContextualActionMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }

}
