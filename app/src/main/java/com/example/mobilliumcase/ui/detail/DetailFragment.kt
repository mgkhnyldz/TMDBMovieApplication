package com.example.mobilliumcase.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import com.example.mobilliumcase.R
import com.example.mobilliumcase.bundle.BundleKeys
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.data.resource.Status
import com.example.mobilliumcase.databinding.FragmentDetailBinding
import com.example.mobilliumcase.ui.main.MainVM
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : DialogFragment() {

    private lateinit var binding: FragmentDetailBinding

    private val detailVM: DetailVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var movie: MovieResult

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

        detailVM.getMovieDetail(movie.workerId!!).observe(viewLifecycleOwner, {
            when(it.status) {
                Status.SUCCESS -> {
                    i { "movieDetail -> ${it.data}" }
                    binding.movie = it.data!!
                }
                Status.ERROR -> e(it.throwable)
                Status.LOADING -> i { "Loading" }
            }
        })
    }
}