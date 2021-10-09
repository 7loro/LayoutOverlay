package com.casper.layoutoverlay.shared.di

import android.content.Context
import com.casper.layoutoverlay.shared.ThemeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PreferenceModule {

    @Singleton
    @Provides
    fun provideThemeProvider(@ApplicationContext context: Context): ThemeProvider {
        return ThemeProvider(context)
    }
}
