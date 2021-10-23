package com.example.mobilliumcase.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieQuery(
    @SerialName("page")
    val page: Int? = 0,
    @SerialName("results")
    val results: List<MovieResult>? = listOf(),
    @SerialName("total_pages")
    val totalPages: Int? = 0,
    @SerialName("total_results")
    val totalResults: Int? = 0
)
