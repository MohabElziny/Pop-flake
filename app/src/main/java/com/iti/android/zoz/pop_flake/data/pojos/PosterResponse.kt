package com.iti.android.zoz.pop_flake.data.pojos

import com.google.gson.annotations.SerializedName

data class PosterResponse(
    @SerializedName("imDbId") var id: String,
    var title: String,
    var posters: List<Poster> = arrayListOf(),
    var errorMessage: String
)

data class Poster(
    var id: String,
    @SerializedName("link") var image: String,
)
