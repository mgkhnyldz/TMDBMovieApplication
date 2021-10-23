package com.example.mobilliumcase.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.databinding.ItemSlideBinding

class SliderAdapter(
    private val context: Context,
    var data: List<MovieResult>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val binding = ItemSlideBinding.inflate(inflater, collection, false)
        binding.movie = data[position]
        collection.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount() = data.size
}
