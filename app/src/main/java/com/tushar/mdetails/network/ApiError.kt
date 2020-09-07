package com.tushar.mdetails.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This class wraps the error response of Movie
 */
data class ApiError(
    @SerializedName("status_code")
    @Expose
    val errorCode: String? = "",
    @SerializedName("status_message")
    @Expose
    val errorMessage: String? = ""
)