package com.tushar.mdetails.data.repository

import android.util.Log
import com.tushar.mdetails.data.local.Movie
import com.tushar.mdetails.data.local.MovieDao
import com.tushar.mdetails.mapper.MoviesResponseMapper
import com.tushar.mdetails.data.remote.GetMovieListResponse
import com.tushar.mdetails.models.State
import com.tushar.mdetails.network.MoviesApiService
import com.tushar.mdetails.utils.Constants
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

    fun getMoviesList(pageNo: Int): Flow<State<List<Movie>>> {
        return object : NetworkBoundRepository<List<Movie>, GetMovieListResponse>(){
            override suspend fun saveRemoteData(response: GetMovieListResponse) {
                response.results?.map { movie ->
                    movie.timestamp = System.currentTimeMillis()
                    delay(10)
                }

                response.results?.let { movies ->
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
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        delay(10)
                    }
                }
            }

            override fun fetchFromLocal(): Flow<List<Movie>> {
                return movieDao.getMovies("",pageNo)
            }

            override suspend fun fetchFromRemote(): Response<GetMovieListResponse> {
                return apiService.getLatestMovies(pageNo)
            }

        }.asFlow()
    }

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
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        delay(10)
                    }
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): Flow<List<Movie>> {
                return movieDao.getMovies("",pageNo)
            }

            override suspend fun fetchFromNetwork(): Response<GetMovieListResponse> {
                return apiService.getLatestMovies(pageNo)
            }

            override fun onFetchFailed(message: String?) {
                Log.e("MoviesRepository", "onFetchFailed: API Failed $message", )
            }

            override fun mapper(): MoviesResponseMapper {
                return MoviesResponseMapper()
            }

        }.asFlow()
    }



    fun getFilteredMovies(query: String): Flow<List<Movie>>{
        return movieDao.getFilteredMovies(query)
    }


}