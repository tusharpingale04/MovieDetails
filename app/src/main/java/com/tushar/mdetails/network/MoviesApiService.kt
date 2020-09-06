package com.tushar.mdetails.network

import com.tushar.mdetails.models.GetMovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/now_playing")
    suspend fun getLatestMovies(
        @Query("page") page: Int
    ): Response<GetMovieListResponse>
}