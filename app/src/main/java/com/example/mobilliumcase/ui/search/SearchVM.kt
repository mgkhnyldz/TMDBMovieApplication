package com.example.mobilliumcase.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mobilliumcase.data.model.MovieQuery
import com.example.mobilliumcase.data.resource.Resource
import com.example.mobilliumcase.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val tmdbRepository: TmdbRepository
) : ViewModel() {

    fun getSearchResultMovies(map: Map<String, String>): LiveData<Resource<MovieQuery>> =
        tmdbRepository.getSearchResultMovies(map).asLiveData()
}