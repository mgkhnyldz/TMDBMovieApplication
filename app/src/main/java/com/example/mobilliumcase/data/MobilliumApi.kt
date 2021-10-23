package com.example.mobilliumcase.data

import com.example.mobilliumcase.data.model.Content
import com.example.mobilliumcase.data.model.MovieQuery
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MobilliumApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@QueryMap queryMap: Map<String, String>): Content


    @GET(" movie/upcoming")
    suspend fun getUpcomingMovies(@QueryMap queryMap: Map<String, String>): Content

    @GET("search/movie")
    suspend fun getSearchResultMovies(@QueryMap queryMap: Map<String, String>): MovieQuery

}