package com.tushar.mdetails.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class, Query::class],version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    abstract fun queryDao() : RecentSearchDao

    companion object{
        const val DATABASE_NAME = "movie_db"
    }
}