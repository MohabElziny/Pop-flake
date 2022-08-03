package com.iti.android.zoz.pop_flake.data.pojos

import com.google.gson.annotations.SerializedName

data class MostPopularMoviesResponse(
    @SerializedName("items") var mostPopularMovies: List<MostPopularMovie> = arrayListOf(),
    var errorMessage: String? = null
)

data class MostPopularMovie(
    var id: String,
    /*var rank: String,
    var title: String,
    var year: String,
    var image: String,
    @SerializedName("imDbRating") var rating: String*/
)