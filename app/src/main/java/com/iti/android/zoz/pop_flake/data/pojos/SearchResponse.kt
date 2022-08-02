package com.iti.android.zoz.pop_flake.data.pojos

data class SearchResponse(
    var expression: String,
    var results: List<Result> = arrayListOf(),
    var errorMessage: String? = null
)

data class Result(
    var id: String,
    var image: String,
    var title: String,
    var description: String
)
