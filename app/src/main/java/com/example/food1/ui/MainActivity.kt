package com.example.food1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.food1.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.recipesFragment,
                R.id.favoriteRecipesFragment,
                R.id.foodJokeFragment
            )
        )
        setupWithNavController(bottomNavigationView,navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        replaceFragment(recipesFragment())
//
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.recipesFragment -> replaceFragment(recipesFragment())
//                R.id.foodJokeFragment -> replaceFragment(foodJokeFragment())
//                R.id.favoriteRecipesFragment -> replaceFragment(favoriteRecipesFragment())
//                else ->{
//
//                }
//
//            }
//            true
//        }
//        navController = findNavController(R.id.navHostFragment)
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.recipesFragment,
//            R.id.favoriteRecipesFragment,
//            R.id.foodJokeFragment
//        ))
//        val bottomNavigationView =findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        setUpWithNavController(bottomNavigationView,navController)
//        setupActionBarWithNavController(navController,appBarConfiguration)
    }
//    private fun replaceFragment(fragment: Fragment){
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.favoriteRecipesFragment,fragment)
//        fragmentTransaction.commit()
//
//    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}