package com.tushar.mdetails.data.remote

import android.os.Parcelable
import com.tushar.mdetails.BuildConfig
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crew(
    val credit_id: String?,
    val department: String?,
    val gender: Int?,
    val id: String?,
    val job: String?,
    val name: String?,
    val profile_path: String?
): Parcelable