package com.example.mobilliumcase.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.example.mobilliumcase.BaseFragment
import com.example.mobilliumcase.R
import com.example.mobilliumcase.data.resource.Status
import com.example.mobilliumcase.databinding.FragmentMainBinding
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val mainVM: MainVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainVM.getNowPlayingMovies()
        mainVM.movieList.observe(viewLifecycleOwner, {
            when(it.status) {
                Status.SUCCESS -> { i { "data -> ${it.data}" } }
                Status.ERROR -> { e(it.throwable) }
                Status.LOADING -> { i { "Loading" } }
            }
        })
    }
}