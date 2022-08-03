package com.iti.android.zoz.pop_flake.ui.trailer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.ResultState
import com.iti.android.zoz.pop_flake.data.pojos.MovieTrailer
import com.iti.android.zoz.pop_flake.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieTrailerViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    private val _trailerUrl: MutableStateFlow<ResultState<MovieTrailer>> =
        MutableStateFlow(ResultState.Loading)
    val trailerUrl get() = _trailerUrl.asStateFlow()

    fun prepareVideo(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieTrailerResponse = async { repository.getMovieTrailer(movieId) }
            emitResponse(movieTrailerResponse.await())
        }
    }

    private suspend fun emitResponse(response: NetworkResponse<MovieTrailer>) {
        when (response) {
            is NetworkResponse.FailureResponse -> _trailerUrl.emit(ResultState.Error(response.errorString))
            is NetworkResponse.SuccessResponse -> {
                if (response.data.videoLinkEmbed.isEmpty())
                    _trailerUrl.emit(ResultState.EmptyResult)
                else
                    _trailerUrl.emit(ResultState.Success(response.data))
            }
        }
    }
}