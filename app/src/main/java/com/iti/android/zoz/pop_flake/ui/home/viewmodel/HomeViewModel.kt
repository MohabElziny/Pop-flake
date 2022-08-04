package com.iti.android.zoz.pop_flake.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.ResultState
import com.iti.android.zoz.pop_flake.data.pojos.*
import com.iti.android.zoz.pop_flake.data.repository.IRepository
import com.iti.android.zoz.pop_flake.domain.IPostersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IRepository,
    private val postersUseCase: IPostersUseCase
) : ViewModel() {

    private lateinit var swapJob: Job
    private var posterList: List<Poster> = emptyList()
    private var currentPosition = 0

    private val _moviesPoster: MutableStateFlow<ResultState<List<Poster>>> =
        MutableStateFlow(ResultState.Loading)
    val moviesPoster get() = _moviesPoster.asStateFlow()

    private val _viewPagerPosition: MutableLiveData<Int> = MutableLiveData()
    val viewPagerPosition: LiveData<Int>
        get() = _viewPagerPosition

    private val _comingSoonMovies: MutableStateFlow<ResultState<List<Movie>>> =
        MutableStateFlow(ResultState.Loading)
    val comingSoonMovies get() = _comingSoonMovies.asStateFlow()

    private val _inTheaterMovies: MutableStateFlow<ResultState<List<Movie>>> =
        MutableStateFlow(ResultState.Loading)
    val inTheaterMovies get() = _inTheaterMovies.asStateFlow()

    private val _topRatedMovies: MutableStateFlow<ResultState<List<TopMovie>>> =
        MutableStateFlow(ResultState.Loading)
    val topRatedMovies get() = _topRatedMovies.asStateFlow()

    private val _boxOfficeMovies: MutableStateFlow<ResultState<List<BoxOfficeMovie>>> =
        MutableStateFlow(ResultState.Loading)
    val boxOfficeMovies get() = _boxOfficeMovies.asStateFlow()

    private var counting = 0
    private val _receivedAllData: MutableLiveData<Boolean> = MutableLiveData()
    val receivedAllData: LiveData<Boolean>
        get() = _receivedAllData

    fun getMovies() {
        counting = 0
        _receivedAllData.value = false
        getMoviesPosters()
        getComingSoonMovies()
        getInTheaterMovies()
        getTopRatedMovies()
        getBoxOfficeMovies()
    }

    private fun getMoviesPosters() {
        _moviesPoster.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val postersResponse = async { postersUseCase.getMoviesPosters() }
            sendPostersResultState(postersResponse.await())
        }
    }

    private suspend fun sendPostersResultState(response: NetworkResponse<List<Poster>>) {
        increaseCounting()
        when (response) {
            is NetworkResponse.FailureResponse -> _moviesPoster.emit(
                ResultState.Error(response.errorString)
            )
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _moviesPoster.emit(ResultState.EmptyResult)
                else {
                    posterList = response.data
                    _moviesPoster.emit(ResultState.Success(response.data))
                }
            }
        }
    }

    private fun getComingSoonMovies() {
        _comingSoonMovies.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val comingSoonResponse = async { repository.getComingSoonMovies() }
            sendComingSoonResultState(comingSoonResponse.await())
        }
    }

    private suspend fun sendComingSoonResultState(response: NetworkResponse<List<Movie>>) {
        increaseCounting()
        when (response) {
            is NetworkResponse.FailureResponse -> _comingSoonMovies.emit(
                ResultState.Error(response.errorString)
            )
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _comingSoonMovies.emit(ResultState.EmptyResult)
                else
                    _comingSoonMovies.emit(ResultState.Success(response.data))
            }
        }
    }

    private fun getInTheaterMovies() {
        _inTheaterMovies.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val inTheaterResponse = async { repository.getInTheatersMovies() }
            sendInTheaterResultState(inTheaterResponse.await())
        }
    }

    private suspend fun sendInTheaterResultState(response: NetworkResponse<List<Movie>>) {
        increaseCounting()
        when (response) {
            is NetworkResponse.FailureResponse -> _inTheaterMovies.emit(
                ResultState.Error(response.errorString)
            )
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _inTheaterMovies.emit(ResultState.EmptyResult)
                else
                    _inTheaterMovies.emit(ResultState.Success(response.data))
            }
        }
    }

    private fun getTopRatedMovies() {
        _topRatedMovies.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val topRatedResponse = async { repository.getTopRatedMovies() }
            sendTopRatedResultState(topRatedResponse.await())
        }
    }

    private suspend fun sendTopRatedResultState(response: NetworkResponse<List<TopMovie>>) {
        increaseCounting()
        when (response) {
            is NetworkResponse.FailureResponse -> _topRatedMovies.emit(
                ResultState.Error(response.errorString)
            )
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _topRatedMovies.emit(ResultState.EmptyResult)
                else
                    _topRatedMovies.emit(ResultState.Success(response.data))
            }
        }
    }

    private fun getBoxOfficeMovies() {
        _boxOfficeMovies.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val boxOfficeResponse = async { repository.getBoxOfficeMovies() }
            sendBoxOfficeResultState(boxOfficeResponse.await())
        }
    }

    private suspend fun sendBoxOfficeResultState(response: NetworkResponse<List<BoxOfficeMovie>>) {
        increaseCounting()
        when (response) {
            is NetworkResponse.FailureResponse -> _boxOfficeMovies.emit(
                ResultState.Error(response.errorString)
            )
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _boxOfficeMovies.emit(ResultState.EmptyResult)
                else
                    _boxOfficeMovies.emit(ResultState.Success(response.data))
            }
        }
    }

    fun startSwap() {
        if (!::swapJob.isInitialized)
            swapJob = setSwapTimer()
    }

    fun stopSwap() {
        if (::swapJob.isInitialized)
            swapJob.cancel()
    }

    fun setManualViewPagerPosition(position: Int) {
        currentPosition = position
    }

    private fun setSwapTimer(): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                delay(3000L)
                currentPosition++
                if (currentPosition == posterList.size)
                    currentPosition = 0
                _viewPagerPosition.postValue(currentPosition)
            }
        }
    }

    private fun increaseCounting() {
        ++counting
        if (counting == 5)
            _receivedAllData.postValue(true)
    }
}