package com.iti.android.zoz.pop_flake.data.remotedatasource

import com.iti.android.zoz.pop_flake.BuildConfig
import com.iti.android.zoz.pop_flake.data.pojos.BoxOfficeMoviesResponse
import com.iti.android.zoz.pop_flake.data.pojos.MoviesResponse
import com.iti.android.zoz.pop_flake.data.pojos.SearchResponse
import com.iti.android.zoz.pop_flake.data.pojos.TopMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

private const val API_KEY = BuildConfig.API_KEY

interface NetworkService {
    @GET("ComingSoon/$API_KEY")
    suspend fun getComingSoonMovies(): Response<MoviesResponse>

    @GET("InTheaters/$API_KEY")
    suspend fun getInTheatersMovies(): Response<MoviesResponse>

    @GET("Top250Movies/$API_KEY")
    suspend fun getTopRatedMovies(): Response<TopMoviesResponse>

    @GET("BoxOffice/$API_KEY")
    suspend fun getBoxOfficeMovies(): Response<BoxOfficeMoviesResponse>

    @GET("SearchTitle/$API_KEY/{query}")
    suspend fun webSearchQuery(
        @Path("query") query: String
    ): Response<SearchResponse>
}