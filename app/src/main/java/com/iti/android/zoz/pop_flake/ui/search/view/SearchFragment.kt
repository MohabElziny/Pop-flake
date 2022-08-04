package com.iti.android.zoz.pop_flake.ui.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.android.zoz.pop_flake.R
import com.iti.android.zoz.pop_flake.data.ResultState
import com.iti.android.zoz.pop_flake.databinding.FragmentSearchBinding
import com.iti.android.zoz.pop_flake.ui.search.adapter.SearchingAdapter
import com.iti.android.zoz.pop_flake.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.iti.android.zoz.pop_flake.utils.getQueryTextChangeStateFlow
import com.iti.android.zoz.pop_flake.utils.showSnackBar
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val searchViewModel: SearchViewModel by viewModels()

    private var _searchBinding: FragmentSearchBinding? = null
    private val searchBinding get() = _searchBinding!!

    private var _searchingAdapter: SearchingAdapter? = null
    private val searchingAdapter get() = _searchingAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeSearchingAdapter()
        setupSearchBar()
        observeSearchResult()
    }

    private fun initializeSearchingAdapter() {
        _searchingAdapter = SearchingAdapter(showMovieDetails)
        searchBinding.searchingRecyclingView.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                RecyclerView.VERTICAL,
                false
            )
            adapter = searchingAdapter
        }
    }

    private fun setupSearchBar() {
        lifecycleScope.launch {
            searchBinding.edtSearch.getQueryTextChangeStateFlow()
                .debounce(500L)
                .filter { query ->
                    return@filter if (query.isEmpty()) {
                        searchingAdapter.setSearchingList(emptyList())
                        false
                    } else
                        true
                }.distinctUntilChanged()
                .collect { query ->
                    searchingAdapter.setSearchingList(emptyList())
                    searchViewModel.makeWebSearch(query)
                }
        }
    }

    private fun observeSearchResult() {
        searchViewModel.searchingMovies.observe(viewLifecycleOwner) { searchResult ->
            searchBinding.searchingProgressBar.visibility = View.INVISIBLE
            when (searchResult) {
                ResultState.EmptyResult -> showSnackBar(getString(R.string.there_is_no_result))
                is ResultState.Error -> showSnackBar(searchResult.errorString)
                ResultState.Loading -> searchBinding.apply {
                    searchingProgressBar.visibility = View.VISIBLE
                    searchingRecyclingView.visibility = View.INVISIBLE
                }
                is ResultState.Success -> {
                    searchingAdapter.setSearchingList(searchResult.data)
                    searchBinding.searchingRecyclingView.visibility = View.VISIBLE
                }
            }
        }
    }

    private val showMovieDetails: (String) -> Unit = { movieId ->
        findNavController().navigate(
            SearchFragmentDirections.actionNavigationSearchToMovieDetailsWebView(movieId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _searchingAdapter = null
        _searchBinding = null
    }
}
