package com.example.mobilliumcase.repository

import com.example.mobilliumcase.data.MobilliumApi
import com.example.mobilliumcase.data.model.Content
import com.example.mobilliumcase.data.model.MovieDetail
import com.example.mobilliumcase.data.model.MovieQuery
import com.example.mobilliumcase.data.resource.Resource
import com.github.ajalt.timberkt.i
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TmdbRepository @Inject constructor(
    private val api: MobilliumApi
) {

    fun getNowPlayingMovies(map: Map<String, String>): Flow<Resource<Content>> {
        return flow {
            emit(Resource.loading(null))
            val nowPlayingMovies = api.getNowPlayingMovies(
                queryMap = map
            )
            emit(Resource.success(nowPlayingMovies))
        }.catch {
            emit(Resource.error(it.message, null, it))
        }.flowOn(Dispatchers.IO)
    }

    fun getUpcomingMovies(map: Map<String, String>): Flow<Resource<Content>> {
        return flow {
            emit(Resource.loading(null))
            i { "requestUpcoming" }
            val upComingMovies = api.getUpcomingMovies(
                queryMap = map
            )
            emit(Resource.success(upComingMovies))
        }.catch {
            emit(Resource.error(it.message, null, it))
        }.flowOn(Dispatchers.IO)
    }

    fun getSearchResultMovies(map: Map<String, String>): Flow<Resource<MovieQuery>> {
        return flow {
            emit(Resource.loading(null))
            val getSearchResultMovies = api.getSearchResultMovies(
                queryMap = map
            )
            emit(Resource.success(getSearchResultMovies))
        }.catch {
            emit(Resource.error(it.message, null, it))
        }.flowOn(Dispatchers.IO)
    }

    fun getMovieDetail(movieId: Long): Flow<Resource<MovieDetail>> {
        return flow {
            emit(Resource.loading(null))
            val movieDetail = api.getMovieDetail(
                movieId = movieId
            )
            emit(Resource.success(movieDetail))
        }.catch {
            emit(Resource.error(it.message, null, it))
        }.flowOn(Dispatchers.IO)
    }

    fun getSimilarMovies(movieId: Long): Flow<Resource<Content>> {
        return flow {
            emit(Resource.loading(null))
            val movieDetail = api.getSimilarMovies(
                movieId = movieId
            )
            emit(Resource.success(movieDetail))
        }.catch {
            emit(Resource.error(it.message, null, it))
        }.flowOn(Dispatchers.IO)
    }
}