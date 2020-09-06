package com.tushar.mdetails.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.tushar.mdetails.data.local.Movie.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

/**
 * Data class for Database entity and Serialization.
 */
@Entity(tableName = TABLE_NAME)
@Parcelize
data class Movie(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var mId: Int = 0,

    @ColumnInfo(name = "movie_id")
    @SerializedName("id")
    var movieId: String,

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    var originalTitle: String,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String,

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releaseDate: String,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String? = "",

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String? = "",

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    var popularity: Double,

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    var voteCount: Long,

    @ColumnInfo(name = "timestamp_in_millis")
    @SerializedName("timestamp_in_millis")
    var timestamp: Long
) : Parcelable {
    companion object {
        const val TABLE_NAME = "movie_table"
    }
}