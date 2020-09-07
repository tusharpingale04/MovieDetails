package com.tushar.mdetails.data.repository

import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import com.tushar.mdetails.data.local.Movie
import com.tushar.mdetails.data.local.MovieDao
import com.tushar.mdetails.data.remote.GetMovieListResponse
import com.tushar.mdetails.mapper.MoviesResponseMapper
import com.tushar.mdetails.network.MoviesApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


@ExperimentalCoroutinesApi
class MoviesRepository
@Inject constructor(
    private val movieDao: MovieDao,
    private val apiService: MoviesApiService
){

    /**
     * Fetches Now Playing from the API or DB
     */
    fun fetchNowPlaying(pageNo: Int) : Flow<Resource<List<Movie>>>{
        return object : NetworkBoundResource<List<Movie>, GetMovieListResponse, MoviesResponseMapper>(){
            override suspend fun saveNetworkResult(item: GetMovieListResponse) {
                item.results?.map { movie ->
                    movie.timestamp = System.currentTimeMillis()
                    delay(10)
                }

                item.results?.let { movies ->
                    for (movie in movies) {
                        movieDao.insert(
                            Movie(
                                movieId = movie.movieId,
                                originalTitle = movie.originalTitle,
                                overview = movie.overview,
                                voteAverage = movie.voteAverage,
                                releaseDate = movie.releaseDate,
                                posterPath = movie.posterPath ?: "",
                                backdropPath = movie.backdropPath ?: "",
                                popularity = movie.popularity,
                                voteCount = movie.voteCount,
                                timestamp = movie.timestamp
                            )
                        )
                    }
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): Flow<List<Movie>> {
                return movieDao.getMovies()
            }

            override suspend fun fetchFromNetwork(): Response<GetMovieListResponse> {
                return apiService.getLatestMovies(pageNo)
            }

            override fun onFetchFailed(message: String?) {
                Log.e("MoviesRepository", "onFetchFailed: API Failed $message")
            }

            override fun mapper(): MoviesResponseMapper {
                return MoviesResponseMapper()
            }

        }.asFlow()
    }

    /**
     * Filter DB based on raw query
     * @param query is the query entered into the search box
     */
    fun getFilteredMovies(query: String): Flow<List<Movie>>{
        val array = query.split(" ")
        var queryString = ""
        array.forEach {
            if(queryString.isNotEmpty()){
                queryString += " OR "
            }
            queryString += "original_title LIKE '$it%'  OR " +
                    "original_title LIKE '% $it'"
        }
        queryString = "SELECT * from movie_table WHERE $queryString"
        val sqLiteQuery = SimpleSQLiteQuery(queryString)
        return movieDao.getFilteredMovies(sqLiteQuery)
    }

}