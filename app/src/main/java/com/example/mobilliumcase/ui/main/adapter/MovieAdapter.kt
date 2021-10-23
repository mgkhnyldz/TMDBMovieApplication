package com.example.mobilliumcase.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.databinding.ItemMovieListBinding

class MovieAdapter(
    private val data: List<MovieResult>
) : RecyclerView.Adapter<MovieAdapter.MovieVH>() {

    class MovieVH(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(movie: MovieResult) {
            binding.movie = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieListBinding.inflate(inflater, parent, false)
        return MovieVH(binding)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bindTo(data[position])
    }

    override fun getItemCount() = data.size
}