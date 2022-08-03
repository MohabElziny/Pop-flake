package com.iti.android.zoz.pop_flake.data.repository

import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.pojos.BoxOfficeMovie
import com.iti.android.zoz.pop_flake.data.pojos.Movie
import com.iti.android.zoz.pop_flake.data.pojos.SearchResult
import com.iti.android.zoz.pop_flake.data.pojos.TopMovie

interface IRepository {
    suspend fun getComingSoonMovies(): NetworkResponse<List<Movie>>

    suspend fun getInTheatersMovies(): NetworkResponse<List<Movie>>

    suspend fun getTopRatedMovies(): NetworkResponse<List<TopMovie>>

    suspend fun getBoxOfficeMovies(): NetworkResponse<List<BoxOfficeMovie>>

    suspend fun webSearchQuery(query: String): NetworkResponse<List<SearchResult>>

    fun getThemeMode(): Int

    fun setThemeMode(themeMode: Int)
}