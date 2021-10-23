package com.example.mobilliumcase.data

import com.example.mobilliumcase.data.model.Content
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MobilliumApi {
    @GET("discover/movie")
    suspend fun getNowPlayingMovies(): Content
}