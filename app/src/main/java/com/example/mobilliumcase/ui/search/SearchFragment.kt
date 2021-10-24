package com.example.mobilliumcase.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.BaseFragment
import com.example.mobilliumcase.R
import com.example.mobilliumcase.bundle.BundleKeys
import com.example.mobilliumcase.data.model.MovieResult
import com.example.mobilliumcase.data.resource.Status
import com.example.mobilliumcase.databinding.FragmentSearchBinding
import com.example.mobilliumcase.extension.hide
import com.example.mobilliumcase.extension.navigateSafe
import com.example.mobilliumcase.extension.show
import com.example.mobilliumcase.helper.searchQueryMap
import com.example.mobilliumcase.listener.OnItemMovieClickListener
import com.example.mobilliumcase.ui.search.adapter.SearchAdapter
import com.github.ajalt.timberkt.e
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search),
    OnItemMovieClickListener {

    private val searchVM: SearchVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        focusEt()
        setObservables()

        binding.rvSearchList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    closeKeyboard()
                }
            }
        })
    }

    private fun closeKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun setObservables() {
        binding.etSearch.doOnTextChanged { text, _, _, count ->
            if (count > 0) {
                binding.ivCloseIcon.show()
            } else {
                binding.ivCloseIcon.hide()
            }

            if (count > 2) {
                searchVM.getSearchResultMovies(
                    map = searchQueryMap(
                        page = 1,
                        query = text.toString()
                    )
                ).observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.SUCCESS -> {
                            searchAdapter = SearchAdapter(it.data!!.results!!, this)
                            binding.rvSearchList.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.rvSearchList.adapter = searchAdapter
                            setVisibility(false)
                        }
                        Status.ERROR -> {
                            e(it.throwable)
                            setVisibility(false)
                        }
                        Status.LOADING -> {
                            setVisibility(true)
                        }
                    }
                })
            }
        }
    }

    private fun setVisibility(isLoading: Boolean) {
        if (isLoading) {
            binding.rvSearchList.hide()
            binding.searchProgressBar.show()
        } else {
            binding.rvSearchList.show()
            binding.searchProgressBar.hide()
        }
    }

    private fun focusEt() {
        binding.etSearch.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    override fun onClicked(movie: MovieResult) {
        closeKeyboard()
        navigateSafe(
            R.id.action_searchFragment_to_detailFragment,
            bundleOf(BundleKeys.MOVIE to movie)
        )
    }
}