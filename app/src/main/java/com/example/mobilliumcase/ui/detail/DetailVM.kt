package com.example.mobilliumcase.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mobilliumcase.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailVM @Inject constructor(
    private val tmdbRepository: TmdbRepository
) : ViewModel() {

    fun getMovieDetail(movieId: Long) = tmdbRepository.getMovieDetail(movieId).asLiveData()

    fun getSimilarMovies(movieId: Long) = tmdbRepository.getSimilarMovies(movieId).asLiveData()


}