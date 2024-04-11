package com.example.food1.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.food1.models.FoodJoke
import com.example.food1.util.Constants.Companion.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke:FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}