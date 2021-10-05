package com.casper.layoutoverlay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.casper.layoutoverlay.databinding.ActivityMainBinding
import com.casper.layoutoverlay.shared.ThemeProvider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
        setTheme()
    }

    private fun initBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun setTheme() {
        val theme = ThemeProvider.getInstance(this).getThemeFromPreferences()
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}