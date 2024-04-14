package com.example.foody.ui

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foody.R
import com.example.foody.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sharedPreferences = getSharedPreferences("mode", Context.MODE_PRIVATE)
        val myValue =  sharedPreferences.getBoolean("modenight", false)

        sharedPreferences = getSharedPreferences("language", Context.MODE_PRIVATE)
        val lang = sharedPreferences.getString("lang","en")
        if(lang=="en"){
            setLocal("en")
        }
        if(lang=="cn"){
            setLocal("cn")
        }
        if(lang=="vn"){
            setLocal("vn")
        }
        when (myValue) {
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }
        navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.recipesFragment,
                R.id.favoriteRecipesFragment,
                R.id.foodJokeFragment,
                R.id.profileFragment2
            )
        )
//        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ffffff")))

        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id != R.id.viewPagerFragment && nd.id != R.id.splashScreenFragment ) {
                navView.visibility = View.VISIBLE
                supportActionBar?.show()
            } else {
                navView.visibility = View.GONE
                supportActionBar?.hide()
            }
        }
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    fun setLocal(langCode:String){
        val languageTag = langCode
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(Locale.forLanguageTag(languageTag))
        )
    }

}
