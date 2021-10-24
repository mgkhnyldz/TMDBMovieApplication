package com.example.mobilliumcase.listener

import com.example.mobilliumcase.data.model.MovieResult

interface OnItemMovieClickListener {
    fun onClicked(movie: MovieResult)
}