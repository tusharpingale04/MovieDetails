package com.tushar.mdetails.di

import android.app.Application
import androidx.room.Room
import com.tushar.mdetails.data.local.AppDatabase
import com.tushar.mdetails.data.local.MovieDao
import com.tushar.mdetails.data.local.RecentSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * This Class provides all the dependencies which are related to DAO and App Database
 */
@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application) : AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(database: AppDatabase): MovieDao{
        return database.movieDao()
    }

    @Singleton
    @Provides
    fun provideRecentSearchDao(database: AppDatabase): RecentSearchDao{
        return database.queryDao()
    }

}