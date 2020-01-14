package com.zup.appkoin.api.response

import com.google.gson.annotations.SerializedName

class GetMoviesResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)