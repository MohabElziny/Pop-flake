package com.iti.android.zoz.pop_flake.data.pojos

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("items") var movies: List<Movie> = arrayListOf(),
    var errorMessage: String? = null
)

data class Movie(
    var id: String,
    var title: String,
    var year: String,
    var releaseState: String,
    var image: String,
    var imDbRating: String? = null,
)
