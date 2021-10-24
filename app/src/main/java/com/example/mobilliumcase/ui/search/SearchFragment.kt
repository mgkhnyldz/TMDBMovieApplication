package com.example.mobilliumcase.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilliumcase.BaseFragment
import com.example.mobilliumcase.R
import com.example.mobilliumcase.bundle.BundleKeys
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.data.resource.Status
import com.example.mobilliumcase.databinding.FragmentSearchBinding
import com.example.mobilliumcase.extension.navigateSafe
import com.example.mobilliumcase.helper.searchQueryMap
import com.example.mobilliumcase.listener.OnItemMovieClickListener
import com.example.mobilliumcase.ui.main.MainVM
import com.example.mobilliumcase.ui.search.adapter.SearchAdapter
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search), OnItemMovieClickListener {

    private val searchVM: SearchVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        focusEt()

        binding.etSearch.doOnTextChanged { text, _, _, count ->
            if (count > 2) {
                searchVM.getSearchResultMovies(
                    map = searchQueryMap(
                        page = 1,
                        query = text.toString()
                    )
                ).observe(viewLifecycleOwner, {
                    when(it.status) {
                        Status.SUCCESS -> {
                            searchAdapter = SearchAdapter(it.data!!.results!!, this)
                            binding.rvSearchList.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.rvSearchList.adapter = searchAdapter
                        }
                        Status.ERROR -> e(it.throwable)
                        Status.LOADING -> i { "Loading" }
                    }
                })
            }
        }
    }

    private fun focusEt() {
        binding.etSearch.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
    }

    override fun onClicked(movie: MovieResult) {
        focusEt()
        navigateSafe(R.id.action_searchFragment_to_detailFragment, bundleOf(BundleKeys.MOVIE to movie))
    }
}