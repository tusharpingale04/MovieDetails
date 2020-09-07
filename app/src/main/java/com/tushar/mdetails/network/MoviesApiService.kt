package com.tushar.mdetails.network

import com.tushar.mdetails.data.remote.GetCastAndCrewResponse
import com.tushar.mdetails.data.remote.GetMovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/now_playing")
    suspend fun getLatestMovies(
        @Query("page") page: Int
    ): Response<GetMovieListResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: String
    ): Response<GetCastAndCrewResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovie(
        @Path("movie_id") movieId: String
    ): Response<GetMovieListResponse>
}