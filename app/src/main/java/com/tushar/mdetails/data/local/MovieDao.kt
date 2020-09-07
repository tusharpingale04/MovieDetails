package com.tushar.mdetails.data.local

import androidx.room.*
import androidx.room.Query
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM movie_table")
    fun getMovies(): Flow<List<Movie>>

    @RawQuery(observedEntities = [Movie::class])
    fun getFilteredMovies(
        query: SupportSQLiteQuery
    ) : Flow<List<Movie>>

}