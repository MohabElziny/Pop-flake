package com.iti.android.zoz.pop_flake.data.datasource.remote

import com.iti.android.zoz.pop_flake.data.pojos.*
import retrofit2.Response

interface IRemoteDataSource {
    suspend fun getComingSoonMovies(): Response<MoviesResponse>

    suspend fun getInTheatersMovies(): Response<MoviesResponse>

    suspend fun getTopRatedMovies(): Response<TopMoviesResponse>

    suspend fun getBoxOfficeMovies(): Response<BoxOfficeMoviesResponse>

    suspend fun webSearchQuery(query: String): Response<SearchResponse>

    suspend fun getMostPopularMovies(): Response<MostPopularMoviesResponse>

    suspend fun getMoviePoster(movie_id: String): Response<PosterResponse>

    suspend fun getMovieTrailer(movie_id: String): Response<MovieTrailer>
}