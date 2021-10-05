package com.casper.layoutoverlay.shared

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import java.security.InvalidParameterException

class ThemeProvider(private val context: Context) {

    fun getThemeFromPreferences(): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val selectedTheme = sharedPreferences.getString(
            context.getString(R.string.preference_key_theme),
            context.getString(R.string.preference_theme_key_system)
        )

        return selectedTheme?.let {
            getTheme(it)
        } ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    fun getTheme(selectedTheme: String): Int = when (selectedTheme) {
        context.getString(R.string.preference_theme_key_dark) -> AppCompatDelegate.MODE_NIGHT_YES
        context.getString(R.string.preference_theme_key_light) -> AppCompatDelegate.MODE_NIGHT_NO
        context.getString(R.string.preference_theme_key_system) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        else -> throw InvalidParameterException("Theme not defined for $selectedTheme")
    }

    fun getThemeDescriptionForPreference(preferenceValue: String?): String =
        when (preferenceValue) {
            context.getString(R.string.preference_theme_key_dark) -> context.getString(R.string.preference_theme_name_dark)
            context.getString(R.string.preference_theme_key_light) -> context.getString(R.string.preference_theme_name_light)
            else -> context.getString(R.string.preference_theme_name_system)
        }
}
