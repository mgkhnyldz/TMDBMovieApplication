package com.example.mobilliumcase.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            tmdbRepository.getNowPlayingMovies().collect {
                _movieList.postValue(it)
            }
        }
    }
}