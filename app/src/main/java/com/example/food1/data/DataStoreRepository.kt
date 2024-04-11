package com.example.food1.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.food1.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.food1.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.food1.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.food1.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.food1.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.food1.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.food1.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.food1.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)
@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    // 1. First we have to define our preferences keys
    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }
    // 2 Now we will create dataStore
    private val dataStore: DataStore<Preferences> = context.dataStore

    // 4
    // So we are basically saving our fields, which we pass inside this function parameters and we are saving
    //them inside our data store preference using those keys
    suspend fun saveMealAndDietType(mealType:String,mealTypeId:Int,dietType:String,dietTypeId:Int){
        dataStore.edit {preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId

        }
    }

    suspend fun saveBackOnline(backOnline:Boolean){
        dataStore.edit {preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> =dataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else{
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType]?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else{
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferenceKeys.backOnline]?: false
            backOnline
        }
}
// 3
data class MealAndDietType(
    val selectedMealType:String,
    val selectedMealTypeId:Int,
    val selectedDietType:String,
    val selectedDietTypeId:Int
)
