package com.example.mobilliumcase.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResult(
    @SerialName("id")
    var workerId: Long? = null,
    var original_title: String = "",
    var poster_path: String? = "",
    var backdrop_path: String? = "",
    var popularity: Double = 0.0,
    var title: String = "",
    var overview: String = "",
    var vote_average: Double = 0.0,
    var release_date: String = "",
)
