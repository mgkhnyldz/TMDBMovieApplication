package com.example.mobilliumcase.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("title")
    val title: String? = "",
    @SerialName("poster_path")
    val posterPath: String? = "",
    @SerialName("backdrop_path")
    val backdropPath: String? = "",
    @SerialName("overview")
    val overview: String? = "",
   /* @SerialName("genres")
    val genres: List<Genre>? = listOf(),
    @SerialName("credits")
    val credits: Credits? = null,*/
    @SerialName("popularity")
    val popularity: Double? = 0.0,
    @SerialName("release_date")
    val releaseDate: String? = "",
    @SerialName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerialName("vote_count")
    val voteCount: Int? = 0,
    @SerialName("runtime")
    val runtime: Int? = 0,
    @SerialName("tagline")
    val tagline: String? = ""
)
