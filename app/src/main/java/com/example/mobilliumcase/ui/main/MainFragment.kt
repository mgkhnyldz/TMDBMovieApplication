package com.example.mobilliumcase.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mobilliumcase.BaseFragment
import com.example.mobilliumcase.R
import com.example.mobilliumcase.bundle.BundleKeys
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.data.resource.Status
import com.example.mobilliumcase.databinding.FragmentMainBinding
import com.example.mobilliumcase.extension.hide
import com.example.mobilliumcase.extension.navigateSafe
import com.example.mobilliumcase.extension.show
import com.example.mobilliumcase.helper.movieQueryMap
import com.example.mobilliumcase.listener.OnItemMovieClickListener
import com.example.mobilliumcase.ui.main.adapter.MovieAdapter
import com.example.mobilliumcase.ui.main.adapter.SliderAdapter
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main),
    OnItemMovieClickListener, SwipeRefreshLayout.OnRefreshListener {

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

    private fun setVisibility(isLoading: Boolean) {
        if (page == 1) {
            if (isLoading) {
                binding.apply {
                    frameSearchView.hide()
                    vpMovie.hide()
                    pageIndicatorView.hide()
                    rvMovieList.hide()
                    mainProgressBar.show()
                }
            } else {
                binding.apply {
                    frameSearchView.show()
                    vpMovie.show()
                    pageIndicatorView.show()
                    rvMovieList.show()
                    mainProgressBar.hide()
                }
            }
        } else {
            if (isLoading) {
                binding.rvProgressBar.show()
            } else {
                binding.rvProgressBar.visibility = View.INVISIBLE
            }
        }

    }

    private fun initUI() {
        binding.rvMovieList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvMovieList.setHasFixedSize(true)
        binding.rvMovieList.isNestedScrollingEnabled = false

        binding.mainSwipe.setOnRefreshListener(this)
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
                    setVisibility(loading)
                    val state = binding.rvMovieList.layoutManager!!.onSaveInstanceState()
                    if (page > 1 && this::movieAdapter.isInitialized) {
                        movieAdapter.updateList(it.data!!.results)
                    } else {
                        movieAdapter =
                            MovieAdapter(it.data!!.results as ArrayList<MovieResult>, this)
                        binding.rvMovieList.adapter = movieAdapter
                    }
                    (binding.rvMovieList.layoutManager as LinearLayoutManager).onRestoreInstanceState(
                        state
                    )
                }
                Status.ERROR -> e(it.throwable)
                Status.LOADING -> {
                    loading = true
                    setVisibility(loading)
                }
            }
        })

        mainVM.getNowPlayingMovies(
            map = movieQueryMap(
                page = 1
            )
        ).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    sliderAdapter =
                        SliderAdapter(requireContext(), it.data!!.results.subList(0, 5), this)
                    binding.vpMovie.adapter = sliderAdapter

                }
                Status.ERROR -> e(it.throwable)
                Status.LOADING -> i { "Loading" }
            }
        })

    }

    private fun pagination() {
        /*binding.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 1){
                    i { "scrolling" }
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
                        binding.mainProgressBar.show()
                    }
                }
            }
        })*/

        binding.mainNestedScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val view = (v as NestedScrollView).getChildAt(v.childCount - 1)
            val diff = view.bottom - (v.height + scrollY)
            if (diff < 10 && !loading) {
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

    override fun onClicked(movie: MovieResult) {
        navigateSafe(
            R.id.action_mainFragment_to_detailFragment,
            bundleOf(BundleKeys.MOVIE to movie)
        )
    }

    override fun onRefresh() {
        mainVM.getUpcomingMovies(
            map = movieQueryMap(
                page = 1
            )
        )
        binding.mainSwipe.isRefreshing = false
    }
}