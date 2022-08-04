package com.iti.android.zoz.pop_flake.domain

import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.pojos.MostPopularMovie
import com.iti.android.zoz.pop_flake.data.pojos.Poster
import com.iti.android.zoz.pop_flake.data.repository.IRepository
import javax.inject.Inject

class PostersUseCase @Inject constructor(private val repository: IRepository) : IPostersUseCase {
    private val posterList: MutableList<Poster> = mutableListOf()

    override suspend fun getMoviesPosters(): NetworkResponse<List<Poster>> {
        return getMostPopularMovies()
    }

    private suspend fun getMostPopularMovies(): NetworkResponse<List<Poster>> {
        val mostPopularMovieResponse = repository.getMostPopularMovies()
        return getMostPopularMoviesPosters(mostPopularMovieResponse)
    }

    private suspend fun getMostPopularMoviesPosters(response: NetworkResponse<List<MostPopularMovie>>): NetworkResponse<List<Poster>> {
        when (response) {
            is NetworkResponse.FailureResponse -> {
                return NetworkResponse.SuccessResponse(emptyList())
            }
            is NetworkResponse.SuccessResponse -> {
                return if (response.data.isEmpty())
                    NetworkResponse.SuccessResponse(emptyList())
                else if (response.data.size > 5) {
                    for (i in 0 until 5)
                        getPoster(response.data[i])
                    return NetworkResponse.SuccessResponse(posterList.toList())
                } else {
                    for (i in 0 until response.data.size)
                        getPoster(response.data[i])
                    return NetworkResponse.SuccessResponse(posterList.toList())
                }
            }
        }
    }

    private suspend fun getPoster(mostPopularMovie: MostPopularMovie) {
        val posterResponse = repository.getMoviePoster(mostPopularMovie.id)
        handlePosterResponse(posterResponse)
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
}