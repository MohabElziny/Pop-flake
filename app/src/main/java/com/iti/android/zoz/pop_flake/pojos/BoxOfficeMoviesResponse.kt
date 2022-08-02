package com.iti.android.zoz.pop_flake.pojos

import com.google.gson.annotations.SerializedName

data class BoxOfficeMoviesResponse(
    @SerializedName("items") var boxOfficeMovies: List<BoxOfficeMovie> = arrayListOf(),
    var errorMessage: String? = null
)

data class BoxOfficeMovie(
    var id: String,
    var rank: String,
    var title: String,
    var image: String,
    var weekend: String,
    var gross: String,
    var weeks: String
)