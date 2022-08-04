package com.iti.android.zoz.pop_flake.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.ResultState
import com.iti.android.zoz.pop_flake.data.pojos.*
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
    private val posterList: MutableList<Poster> = mutableListOf()

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
        posterList.clear()
        getMostPopularMovies()
        getComingSoonMovies()
        getInTheaterMovies()
        getTopRatedMovies()
        getBoxOfficeMovies()
    }

    private fun getMostPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val mostPopularMovieResponse = async { repository.getMostPopularMovies() }
            getMostPopularMoviesPosters(mostPopularMovieResponse.await())
        }
    }

    private fun getMostPopularMoviesPosters(response: NetworkResponse<List<MostPopularMovie>>) {
        when (response) {
            is NetworkResponse.FailureResponse -> {
                _postersList.postValue(emptyList())
                increaseCounting()
            }
            is NetworkResponse.SuccessResponse -> {
                if (response.data.isEmpty())
                    _postersList.postValue(emptyList())
                else if (response.data.size > 5) {
                    for (i in 0 until 5)
                        getPostersList(response.data[i])
                    _postersList.postValue(posterList)
                } else {
                    for (i in 0 until response.data.size)
                        getPostersList(response.data[i])
                    _postersList.postValue(posterList)
                }
                increaseCounting()
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
        _inTheaterMovies.value = ResultState.Loading
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
        _topRatedMovies.value = ResultState.Loading
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
        _boxOfficeMovies.value = ResultState.Loading
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

    private fun getPostersList(mostPopularMovie: MostPopularMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            val posterResponse = async { repository.getMoviePoster(mostPopularMovie.id) }
            handlePosterResponse(posterResponse.await())
        }
    }

    private fun handlePosterResponse(response: NetworkResponse<Poster>) {
        when (response) {
            is NetworkResponse.FailureResponse -> {}
            is NetworkResponse.SuccessResponse -> {
                if (response.data.id.isNotEmpty())
                    posterList.add(response.data)
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