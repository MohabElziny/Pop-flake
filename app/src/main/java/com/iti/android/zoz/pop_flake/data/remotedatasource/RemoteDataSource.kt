package com.iti.android.zoz.pop_flake.data.remotedatasource

import com.iti.android.zoz.pop_flake.data.pojos.BoxOfficeMoviesResponse
import com.iti.android.zoz.pop_flake.data.pojos.MoviesResponse
import com.iti.android.zoz.pop_flake.data.pojos.TopMoviesResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val networkService: NetworkService
) : IRemoteDataSource {
    override suspend fun getComingSoonMovies(): Response<MoviesResponse> =
        networkService.getComingSoonMovies()

    override suspend fun getInTheatersMovies(): Response<MoviesResponse> =
        networkService.getInTheatersMovies()

    override suspend fun getTopRatedMovies(): Response<TopMoviesResponse> =
        networkService.getTopRatedMovies()

    override suspend fun getBoxOfficeMovies(): Response<BoxOfficeMoviesResponse> =
        networkService.getBoxOfficeMovies()
}