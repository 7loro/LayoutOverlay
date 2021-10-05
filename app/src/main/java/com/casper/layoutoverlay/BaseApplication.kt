package com.casper.layoutoverlay

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.casper.layoutoverlay.shared.ThemeProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {
    @Inject
    lateinit var themeProvider: ThemeProvider

    override fun onCreate() {
        super.onCreate()
        setTheme()
    }

    private fun setTheme() {
        val theme = themeProvider.getThemeFromPreferences()
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}