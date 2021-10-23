package com.example.mobilliumcase.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilliumcase.BaseFragment
import com.example.mobilliumcase.R
import com.example.mobilliumcase.data.resource.Status
import com.example.mobilliumcase.databinding.FragmentMainBinding
import com.example.mobilliumcase.extension.navigateSafe
import com.example.mobilliumcase.ui.main.adapter.MovieAdapter
import com.example.mobilliumcase.ui.main.adapter.SliderAdapter
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val mainVM: MainVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var sliderAdapter: SliderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainVM.getNowPlayingMovies()
        mainVM.movieList.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    sliderAdapter = SliderAdapter(requireContext(), it.data!!.results.subList(0, 5))
                    binding.vpMovie.adapter = sliderAdapter
                }
                Status.ERROR -> e(it.throwable)
                Status.LOADING -> i { "Loading" }
            }
        })

        mainVM.getUpcomingMovies().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    movieAdapter = MovieAdapter(it.data!!.results)
                    binding.rvMovieList.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    binding.rvMovieList.adapter = movieAdapter
                }
                Status.ERROR -> e(it.throwable)
                Status.LOADING -> i { "Loading" }
            }
        })

        binding.frameSearchView.setOnClickListener {
            navigateSafe(R.id.action_mainFragment_to_searchFragment)
        }

    }
}