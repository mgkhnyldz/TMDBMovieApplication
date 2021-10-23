package com.example.mobilliumcase.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.databinding.ItemSearchBinding

class SearchAdapter(
    private val data: List<MovieResult>
): RecyclerView.Adapter<SearchAdapter.SearchVH>() {
    class SearchVH(private val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindTo(movie: MovieResult) {
            binding.movie = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return SearchVH(binding)
    }

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        holder.bindTo(data[position])
    }

    override fun getItemCount() = data.size
}