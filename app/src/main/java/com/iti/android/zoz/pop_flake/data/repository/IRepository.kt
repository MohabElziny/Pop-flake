package com.iti.android.zoz.pop_flake.data.repository

import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.pojos.*

interface IRepository {
    suspend fun getComingSoonMovies(): NetworkResponse<List<Movie>>

    suspend fun getInTheatersMovies(): NetworkResponse<List<Movie>>

    suspend fun getTopRatedMovies(): NetworkResponse<List<TopMovie>>

    suspend fun getBoxOfficeMovies(): NetworkResponse<List<BoxOfficeMovie>>

    suspend fun getMostPopularMovies(): NetworkResponse<List<MostPopularMovie>>

    suspend fun getMoviePoster(movie_id: String): NetworkResponse<Poster>

    suspend fun getMovieTrailer(movie_id: String): NetworkResponse<MovieTrailer>

    suspend fun webSearchQuery(query: String): NetworkResponse<List<SearchResult>>

    fun getThemeMode(): Int

    fun setThemeMode(themeMode: Int)
}