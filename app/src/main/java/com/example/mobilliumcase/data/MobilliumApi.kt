package com.example.mobilliumcase.data

import com.example.mobilliumcase.data.model.Content
import com.example.mobilliumcase.data.model.MovieDetail
import com.example.mobilliumcase.data.model.MovieQuery
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MobilliumApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@QueryMap queryMap: Map<String, String>): Content


    @GET(" movie/upcoming")
    suspend fun getUpcomingMovies(@QueryMap queryMap: Map<String, String>): Content

    @GET("search/movie")
    suspend fun getSearchResultMovies(@QueryMap queryMap: Map<String, String>): MovieQuery

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Long): MovieDetail

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Long): Content

}