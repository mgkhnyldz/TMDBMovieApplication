package com.example.mobilliumcase.ui.main

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.BaseFragment
import com.example.mobilliumcase.R
import com.example.mobilliumcase.bundle.BundleKeys
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.data.resource.Status
import com.example.mobilliumcase.databinding.FragmentMainBinding
import com.example.mobilliumcase.extension.navigateSafe
import com.example.mobilliumcase.helper.movieQueryMap
import com.example.mobilliumcase.ui.main.adapter.MovieAdapter
import com.example.mobilliumcase.ui.main.adapter.SliderAdapter
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main), MovieAdapter.OnItemClickListener {

    private val mainVM: MainVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var sliderAdapter: SliderAdapter

    private var page: Int = 1
    private var loading: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setObservables()
        pagination()

        binding.frameSearchView.setOnClickListener {
            navigateSafe(R.id.action_mainFragment_to_searchFragment)
        }

    }

    private fun initUI() {
        binding.rvMovieList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvMovieList.setHasFixedSize(true)
    }

    private fun setObservables() {
        mainVM.getUpcomingMovies(
            map = movieQueryMap(
                page = page
            )
        )
        mainVM.movies.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    loading = false
                    val state = binding.rvMovieList.layoutManager!!.onSaveInstanceState()
                    if (page > 1 && this::movieAdapter.isInitialized) {
                        movieAdapter.updateList(it.data!!.results)
                    } else {
                        movieAdapter = MovieAdapter(it.data!!.results as ArrayList<MovieResult>, this)
                        binding.rvMovieList.adapter = movieAdapter
                    }
                    (binding.rvMovieList.layoutManager as LinearLayoutManager).onRestoreInstanceState(state)
                }
                Status.ERROR -> e(it.throwable)
                Status.LOADING -> loading = true
            }


        })

        mainVM.getNowPlayingMovies(
            map = movieQueryMap(
                page = 1
            )
        ).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    sliderAdapter = SliderAdapter(requireContext(), it.data!!.results.subList(0, 5))
                    binding.vpMovie.adapter = sliderAdapter

                }
                Status.ERROR -> e(it.throwable)
                Status.LOADING -> i { "Loading" }
            }
        })

    }

    private fun pagination() {
        binding.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 1){
                    val layoutManager = binding.rvMovieList.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val isReachedToEnd = lastVisibleItemPosition + 5 > totalItemCount
                    if (isReachedToEnd && totalItemCount > 0 && !loading) {
                        page += 1
                        mainVM.getUpcomingMovies(
                            map = movieQueryMap(
                                page = page
                            )
                        )
                        loading = true
                    }
                }
            }
        })
    }

    override fun onClicked(movie: MovieResult) {
        i { "movie -> $movie" }
        navigateSafe(R.id.action_mainFragment_to_detailFragment, bundleOf(BundleKeys.MOVIE to movie))
    }
}