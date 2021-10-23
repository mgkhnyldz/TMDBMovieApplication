package com.example.mobilliumcase.ui.main

import androidx.lifecycle.*
import com.example.mobilliumcase.data.model.Content
import com.example.mobilliumcase.data.resource.Resource
import com.example.mobilliumcase.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val tmdbRepository: TmdbRepository
) : ViewModel() {
    val _movieList: MutableLiveData<Resource<Content>> = MutableLiveData()
    val movieList: LiveData<Resource<Content>>
        get() = _movieList

    fun getNowPlayingMovies(map: Map<String, String>) {
        viewModelScope.launch {
            tmdbRepository.getNowPlayingMovies(map).collect {
                _movieList.postValue(it)
            }
        }
    }

    fun getUpcomingMovies(map: Map<String, String>): LiveData<Resource<Content>> =
        tmdbRepository.getUpcomingMovies(map).asLiveData()
}