package com.iti.android.zoz.pop_flake.pojos

import com.google.gson.annotations.SerializedName

data class TopMoviesResponse(
    @SerializedName("items") var topMovies: List<TopMovie> = arrayListOf(),
    var errorMessage: String? = null
)

data class TopMovie(
    var id: String,
    var rank: String,
    var title: String,
    var year: String,
    var image: String,
    var imDbRating: String,
)