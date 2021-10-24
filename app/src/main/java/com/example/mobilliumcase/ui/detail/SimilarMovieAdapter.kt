package com.example.mobilliumcase.ui.detail

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.R
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.databinding.ItemSimilarBinding
import com.example.mobilliumcase.listener.OnItemMovieClickListener

class SimilarMovieAdapter(
    private val data: List<MovieResult>,
    private val onItemMovieClickListener: OnItemMovieClickListener
) : RecyclerView.Adapter<SimilarMovieAdapter.SimilarMovieVH>() {
    class SimilarMovieVH(val binding: ItemSimilarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(movie: MovieResult, clickListener: OnItemMovieClickListener) {
            binding.movie = movie
            binding.root.setOnClickListener {
                clickListener.onClicked(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieVH {
        val inlfater = LayoutInflater.from(parent.context)
        val view = inlfater.inflate(R.layout.item_similar, parent, false)
        val binding = ItemSimilarBinding.bind(view)
        return SimilarMovieVH(binding)
    }

    override fun onBindViewHolder(holder: SimilarMovieVH, position: Int) {
        holder.bindTo(data[position], onItemMovieClickListener)
    }

    override fun getItemCount() = data.size
}