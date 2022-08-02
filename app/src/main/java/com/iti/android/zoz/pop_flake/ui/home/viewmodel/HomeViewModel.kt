package com.iti.android.zoz.pop_flake.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.ResultState
import com.iti.android.zoz.pop_flake.data.pojos.BoxOfficeMovie
import com.iti.android.zoz.pop_flake.data.pojos.Movie
import com.iti.android.zoz.pop_flake.data.pojos.Poster
import com.iti.android.zoz.pop_flake.data.pojos.TopMovie
import com.iti.android.zoz.pop_flake.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    private lateinit var swapJob: Job
    private var currentPosition = 0

    private val _postersList: MutableLiveData<List<Poster>> = MutableLiveData()
    val postersList: LiveData<List<Poster>>
        get() = _postersList

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
        getComingSoonMovies()
        getInTheaterMovies()
        getTopRatedMovies()
        getBoxOfficeMovies()
    }

    private fun getComingSoonMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val comingSoonResponse = async { repository.getComingSoonMovies() }
            sendComingSoonResultState(comingSoonResponse.await())
        }
    }

    private fun sendComingSoonResultState(response: NetworkResponse<List<Movie>>) {
        increaseCounting()
        when (response) {
            is NetworkResponse.FailureResponse -> _comingSoonMovies.value =
                ResultState.Error(response.errorString)
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _comingSoonMovies.value = ResultState.EmptyResult
                else
                    _comingSoonMovies.value = ResultState.Success(response.data)
            }
        }
    }

    private fun getInTheaterMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val inTheaterResponse = async { repository.getInTheatersMovies() }
            sendInTheaterResultState(inTheaterResponse.await())
        }
    }

    private fun sendInTheaterResultState(response: NetworkResponse<List<Movie>>) {
        increaseCounting()
        when (response) {
            is NetworkResponse.FailureResponse -> _inTheaterMovies.value =
                ResultState.Error(response.errorString)
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _inTheaterMovies.value = ResultState.EmptyResult
                else
                    _inTheaterMovies.value = ResultState.Success(response.data)
            }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val topRatedResponse = async { repository.getTopRatedMovies() }
            sendTopRatedResultState(topRatedResponse.await())
        }
    }

    private fun sendTopRatedResultState(response: NetworkResponse<List<TopMovie>>) {
        increaseCounting()
        when (response) {
            is NetworkResponse.FailureResponse -> _topRatedMovies.value =
                ResultState.Error(response.errorString)
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _topRatedMovies.value = ResultState.EmptyResult
                else
                    _topRatedMovies.value = ResultState.Success(response.data)
            }
        }
    }

    private fun getBoxOfficeMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val boxOfficeResponse = async { repository.getBoxOfficeMovies() }
            sendBoxOfficeResultState(boxOfficeResponse.await())
        }
    }

    private fun sendBoxOfficeResultState(response: NetworkResponse<List<BoxOfficeMovie>>) {
        increaseCounting()
        when (response) {
            is NetworkResponse.FailureResponse -> _boxOfficeMovies.value =
                ResultState.Error(response.errorString)
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _boxOfficeMovies.value = ResultState.EmptyResult
                else
                    _boxOfficeMovies.value = ResultState.Success(response.data)
            }
        }
    }

    fun getPostersList() {
        _postersList.value = posterList
    }

    fun startSwap() {
        swapJob = setSwapTimer()
    }

    fun stopSwap() {
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
        counting++
        if (counting == 4)
            _receivedAllData.postValue(true)
    }

    private val posterList: List<Poster> = listOf(
        Poster("https://imdb-api.com/posters/original/8IB2e4r4oVhHnANbnm7O3Tj6tF8.jpg"),
        Poster("https://imdb-api.com/posters/original/yI6X2cCM5YPJtxMhUd3dPGqDAhw.jpg"),
        Poster("https://imdb-api.com/posters/original/og6S0aTZU6YUJAbqxeKjCa3kY1E.jpg"),
        Poster("https://imdb-api.com/posters/original/8IB2e4r4oVhHnANbnm7O3Tj6tF8.jpg"),
        Poster("https://imdb-api.com/posters/original/og6S0aTZU6YUJAbqxeKjCa3kY1E.jpg"),
    )
}