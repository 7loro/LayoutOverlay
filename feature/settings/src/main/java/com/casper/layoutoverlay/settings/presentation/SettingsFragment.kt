package com.casper.layoutoverlay.settings.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.casper.layoutoverlay.settings.R
import com.casper.layoutoverlay.shared.ThemeProvider

class SettingsFragment : PreferenceFragmentCompat() {

    private val themeProvider by lazy {
        ThemeProvider.getInstance(requireContext())
    }
    private val themePreference by lazy {
        findPreference<ListPreference>(getString(ThemeProvider.PREF_KEY_THEME_RESOURCE_ID))
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        setThemePreference()
    }

    private fun setThemePreference() {
        themePreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                if (newValue is String) {
                    val theme = themeProvider.getTheme(newValue)
                    AppCompatDelegate.setDefaultNightMode(theme)
                }
                true
            }
        themePreference?.summaryProvider = getThemeSummaryProvider()
    }

    private fun getThemeSummaryProvider() =
        Preference.SummaryProvider<ListPreference> { preference ->
            themeProvider.getThemeDescriptionForPreference(preference.value)
        }
}
