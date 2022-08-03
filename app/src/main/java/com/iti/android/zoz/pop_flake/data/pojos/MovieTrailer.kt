package com.iti.android.zoz.pop_flake.data.pojos

import com.google.gson.annotations.SerializedName

data class MovieTrailer(
    @SerializedName("imDbId") var id: String,
    var title: String,
    @SerializedName("link") var videoLink: String,
    @SerializedName("linkEmbed") var videoLinkEmbed: String,
    var errorMessage: String? = null
)