package com.example.food1.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.food1.models.FoodRecipe
import com.example.food1.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity (
    var foodRecipe: FoodRecipe
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}