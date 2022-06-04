package com.example.newsdagger2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.newsdagger2.App.App
import com.example.newsdagger2.cache.Cache
import com.example.newsdagger2.databinding.ActivityMainBinding
import com.example.newsdagger2.di.component.AppComponent

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO) {
            Cache.instance!!.saveNightMode(true)
        } else {
            Cache.instance!!.saveNightMode(false)
        }


        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val id = destination.id
            if (id == R.id.splashFragment || id == R.id.getStartedFragment ||
                id == R.id.selectFavouriteFragment || id == R.id.newsFragment ||
                id == R.id.newsListFragment) {
                binding.bottomNavigationView.hide()
            } else binding.bottomNavigationView.show()

            if (id == R.id.homeFragment) {
                binding.bottomNavigationView.selectedItemId = R.id.homeFragment
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    if (navController.currentDestination?.id != R.id.homeFragment)
                        navController.navigate(R.id.homeFragment)
                }
                R.id.categoriesFragment -> navController.navigate(R.id.categoriesFragment)
                R.id.bookmarkFragment -> navController.navigate(R.id.bookmarkFragment)
                R.id.profileFragment -> navController.navigate(R.id.profileFragment)
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }
}