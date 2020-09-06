package com.tushar.mdetails.mapper

import com.tushar.mdetails.data.repository.NetworkResponseMapper
import com.tushar.mdetails.models.GetMovieListResponse

class MoviesResponseMapper : NetworkResponseMapper<GetMovieListResponse> {
    override fun onLastPage(response: GetMovieListResponse): Boolean {
        return response.page == response.totalPages
    }
}