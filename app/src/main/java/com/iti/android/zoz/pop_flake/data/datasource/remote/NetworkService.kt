package com.iti.android.zoz.pop_flake.data.datasource.remote

import com.iti.android.zoz.pop_flake.BuildConfig.API_KEY
import com.iti.android.zoz.pop_flake.data.pojos.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {
    @GET("ComingSoon/$API_KEY")
    suspend fun getComingSoonMovies(): Response<MoviesResponse>

    @GET("InTheaters/$API_KEY")
    suspend fun getInTheatersMovies(): Response<MoviesResponse>

    @GET("Top250Movies/$API_KEY")
    suspend fun getTopRatedMovies(): Response<TopMoviesResponse>

    @GET("BoxOffice/$API_KEY")
    suspend fun getBoxOfficeMovies(): Response<BoxOfficeMoviesResponse>

    @GET("MostPopularMovies/$API_KEY")
    suspend fun getMostPopularMovies(): Response<MostPopularMoviesResponse>

    @GET("Posters/$API_KEY/{movie_id}")
    suspend fun getMoviePoster(
        @Path("movie_id") movie_id: String
    ): Response<PosterResponse>

    @GET("Trailer/$API_KEY/{movie_id}")
    suspend fun getMovieTrailer(
        @Path("movie_id") movie_id: String
    ): Response<MovieTrailer>

    @GET("SearchTitle/$API_KEY/{query}")
    suspend fun webSearchQuery(
        @Path("query") query: String
    ): Response<SearchResponse>
}