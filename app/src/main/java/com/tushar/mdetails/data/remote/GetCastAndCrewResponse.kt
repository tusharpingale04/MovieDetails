package com.tushar.mdetails.data.remote

import com.tushar.mdetails.data.repository.NetworkResponseModel
import com.tushar.mdetails.models.BaseResponse

class GetCastAndCrewResponse(
    val id: Int? = null,
    val cast: ArrayList<Cast>? = null,
    val crew: ArrayList<Crew>? = null
) : NetworkResponseModel