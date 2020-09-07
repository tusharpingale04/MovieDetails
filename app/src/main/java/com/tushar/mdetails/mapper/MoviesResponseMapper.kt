package com.tushar.mdetails.mapper

import com.tushar.mdetails.data.repository.NetworkResponseMapper
import com.tushar.mdetails.data.remote.GetMovieListResponse

class MoviesResponseMapper : NetworkResponseMapper<GetMovieListResponse> {
    override fun onLastPage(response: GetMovieListResponse): Boolean {
        return true
    }
}