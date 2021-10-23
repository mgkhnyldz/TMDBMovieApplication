package com.example.mobilliumcase.repository

import com.example.mobilliumcase.data.MobilliumApi
import com.example.mobilliumcase.data.model.Content
import com.example.mobilliumcase.data.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TmdbRepository @Inject constructor(
    private val api: MobilliumApi
){

    fun getNowPlayingMovies(): Flow<Resource<Content>> {
        return flow {
            emit(Resource.loading(null))
            val nowPlayingMovies = api.getNowPlayingMovies()
            emit(Resource.success(nowPlayingMovies))
        }.catch {
            emit(Resource.error(it.message, null, it))
        }.flowOn(Dispatchers.IO)
    }
}