package com.example.food1.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.food1.data.database.entities.FavoritesEntity
import com.example.food1.data.database.entities.FoodJokeEntity
import com.example.food1.data.database.entities.RecipesEntity
// , FavoritesEntity::class
@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class, FoodJokeEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
}
