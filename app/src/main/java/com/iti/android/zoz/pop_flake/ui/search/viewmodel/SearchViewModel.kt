package com.iti.android.zoz.pop_flake.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.ResultState
import com.iti.android.zoz.pop_flake.data.pojos.SearchResult
import com.iti.android.zoz.pop_flake.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val _searchingMovies:
            MutableLiveData<ResultState<List<SearchResult>>> = MutableLiveData()
    val searchingMovies: LiveData<ResultState<List<SearchResult>>> get() = _searchingMovies

    fun makeWebSearch(query: String) {
        _searchingMovies.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val searchResponse = async { repository.webSearchQuery(query) }
            setSearchResponseResult(searchResponse.await())
        }
    }

    private fun setSearchResponseResult(response: NetworkResponse<List<SearchResult>>) {
        when (response) {
            is NetworkResponse.FailureResponse -> _searchingMovies.postValue(
                ResultState.Error(
                    response.errorString
                )
            )
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _searchingMovies.postValue(ResultState.EmptyResult)
                else
                    _searchingMovies.postValue(ResultState.Success(response.data))
            }
        }
    }

}