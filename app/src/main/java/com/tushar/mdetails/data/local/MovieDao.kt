package com.tushar.mdetails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tushar.mdetails.utils.Constants.PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM movie_table")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("""
        SELECT * from movie_table
        WHERE original_title LIKE '%' || :query || '%'
        OR overview LIKE '%' || :query || '%'
        ORDER BY id ASC
        LIMIT(:pageNo * :pageSize)
    """)
    fun getMovies(
        query: String,
        pageNo: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE
    ) : Flow<List<Movie>>

    @Query("""
        SELECT * from movie_table
        WHERE original_title LIKE '%' || :query || '%'
        ORDER BY id ASC
    """)
    fun getFilteredMovies(
        query: String
    ) : Flow<List<Movie>>

}