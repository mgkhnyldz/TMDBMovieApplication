package com.example.mobilliumcase.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Content(
    @SerialName("page") val page : Int,
    @SerialName("total_results") val total_results : Int,
    @SerialName("total_pages") val total_pages : Int,
    @SerialName("results") val results : List<MovieResult>
)
