package com.example.mobilliumcase.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.databinding.ItemSearchBinding
import com.example.mobilliumcase.listener.OnItemMovieClickListener

class SearchAdapter(
    private val data: List<MovieResult>,
    private val onItemMovieClickListener: OnItemMovieClickListener
): RecyclerView.Adapter<SearchAdapter.SearchVH>() {
    class SearchVH(private val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindTo(movie: MovieResult, onClickListener: OnItemMovieClickListener) {
            binding.movie = movie
            binding.root.setOnClickListener {
                onClickListener.onClicked(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return SearchVH(binding)
    }

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        holder.bindTo(data[position], onItemMovieClickListener)
    }

    override fun getItemCount() = data.size
}