package com.casper.layoutoverlay.overlay.data.di

import android.content.Context
import androidx.room.Room
import com.casper.layoutoverlay.overlay.data.OverlayDao
import com.casper.layoutoverlay.overlay.data.OverlayDatabase
import com.casper.layoutoverlay.overlay.data.repository.OverlayRepositoryImpl
import com.casper.layoutoverlay.overlay.domain.repository.OverlayRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OverlayDataModule {
    /**
     * Binds 는 구현체를 우리 앱 내에 가지고 있는 경우
     * Provide 는 interface 만 가지고 있는 경우
     */
    @Binds
    abstract fun bindsOverlayRepository(
        repository: OverlayRepositoryImpl
    ): OverlayRepository


    @InstallIn(SingletonComponent::class)
    @Module
    internal object DatabaseModule {
        @Provides
        @Singleton
        fun provideOverlayDao(overlayDatabase: OverlayDatabase): OverlayDao {
            return overlayDatabase.overlayDao()
        }

        @Provides
        @Singleton
        fun provideOverlayDatabase(@ApplicationContext appContext: Context): OverlayDatabase {
            return Room.databaseBuilder(
                appContext,
                OverlayDatabase::class.java,
                "overlay"
            ).build()
        }
    }
}
