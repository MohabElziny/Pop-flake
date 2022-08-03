package com.iti.android.zoz.pop_flake.data.datasource.remote

import com.iti.android.zoz.pop_flake.data.pojos.*
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

    override suspend fun webSearchQuery(query: String): Response<SearchResponse> =
        networkService.webSearchQuery(query)

    override suspend fun getMostPopularMovies(): Response<MostPopularMoviesResponse> =
        networkService.getMostPopularMovies()

    override suspend fun getMoviePoster(movie_id: String): Response<PosterResponse> =
        networkService.getMoviePoster(movie_id)
}