package com.tushar.mdetails.models

import com.google.gson.annotations.SerializedName
import com.tushar.mdetails.data.repository.NetworkResponseModel

/**
 * This class contains the Base Response of TMDB API RESPONSE
 */
open class BaseListResponse<Item>(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("results") var results: ArrayList<Item>? = null
) : NetworkResponseModel