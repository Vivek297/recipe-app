package com.example.food1.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.food1.models.Result
import com.example.food1.util.Constants.Companion.FAVORITE_RECIPES_TABLE

//
@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result:Result
)