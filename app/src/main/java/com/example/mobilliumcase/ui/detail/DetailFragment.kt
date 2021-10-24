package com.example.mobilliumcase.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilliumcase.R
import com.example.mobilliumcase.bundle.BundleKeys
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.data.resource.Status
import com.example.mobilliumcase.databinding.FragmentDetailBinding
import com.example.mobilliumcase.extension.navigateSafe
import com.example.mobilliumcase.listener.OnItemMovieClickListener
import com.example.mobilliumcase.ui.main.adapter.MovieAdapter
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : DialogFragment(), OnItemMovieClickListener {

    private lateinit var binding: FragmentDetailBinding

    private val detailVM: DetailVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var movie: MovieResult

    private lateinit var similarMovieAdapter: SimilarMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        binding = FragmentDetailBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = requireArguments().getParcelable(BundleKeys.MOVIE)!!
        initUI()
        setObservables()
    }

    private fun setObservables() {
        detailVM.getMovieDetail(movie.workerId!!).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.movie = it.data!!
                }
                Status.ERROR -> e(it.throwable)
                Status.LOADING -> i { "Loading" }
            }
        })

        detailVM.getSimilarMovies(movie.workerId!!).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    similarMovieAdapter = SimilarMovieAdapter(it.data!!.results, this)
                    binding.rvSimilarMovieList.adapter = similarMovieAdapter
                }
                Status.ERROR -> e(it.throwable)
                Status.LOADING -> i { "Loading" }
            }
        })
    }

    private fun initUI() {
        binding.rvSimilarMovieList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvSimilarMovieList.setHasFixedSize(true)
    }

    override fun onClicked(movie: MovieResult) {
        i { "kliklendi" }
        navigateSafe(R.id.action_detailFragment_self, bundleOf(BundleKeys.MOVIE to movie))
    }
}