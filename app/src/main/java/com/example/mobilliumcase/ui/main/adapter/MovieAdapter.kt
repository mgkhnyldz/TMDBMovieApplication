package com.example.mobilliumcase.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.databinding.ItemMovieListBinding
import com.github.ajalt.timberkt.i

class MovieAdapter(
    private val data: ArrayList<MovieResult>,
    val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieVH>() {

    class MovieVH(val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(movie: MovieResult, clickListener: OnItemClickListener) {
            binding.movie = movie
            binding.root.setOnClickListener {
                clickListener.onClicked(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieListBinding.inflate(inflater, parent, false)
        return MovieVH(binding)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bindTo(data[position], itemClickListener)
    }

    override fun getItemCount() = data.size

    fun updateList(list: List<MovieResult>) {
        data.addAll(list)
        notifyItemRangeChanged(itemCount, list.size)
    }

    interface OnItemClickListener {
        fun onClicked(movie: MovieResult)
    }
}