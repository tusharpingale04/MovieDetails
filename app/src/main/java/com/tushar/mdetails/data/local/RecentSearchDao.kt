package com.tushar.mdetails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(query: com.tushar.mdetails.data.local.Query)

    @Query("SELECT * FROM query_table ORDER BY id DESC")
    fun getAllSearchQueries(): Flow<List<com.tushar.mdetails.data.local.Query>>

    @Query("DELETE FROM query_table where id NOT IN (SELECT id from query_table ORDER BY id DESC LIMIT 5)")
    suspend fun deleteAdditionalQueries()
}