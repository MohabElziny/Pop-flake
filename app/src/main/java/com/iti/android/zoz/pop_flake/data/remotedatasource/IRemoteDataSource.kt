package com.iti.android.zoz.pop_flake.data.remotedatasource

import com.iti.android.zoz.pop_flake.data.pojos.BoxOfficeMoviesResponse
import com.iti.android.zoz.pop_flake.data.pojos.MoviesResponse
import com.iti.android.zoz.pop_flake.data.pojos.TopMoviesResponse
import retrofit2.Response

interface IRemoteDataSource {
    suspend fun getComingSoonMovies(): Response<MoviesResponse>

    suspend fun getInTheatersMovies(): Response<MoviesResponse>

    suspend fun getTopRatedMovies(): Response<TopMoviesResponse>

    suspend fun getBoxOfficeMovies(): Response<BoxOfficeMoviesResponse>
}