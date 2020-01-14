package com.zup.appkoin.view.adapter.listeners

import android.widget.ImageView
import com.zup.appkoin.api.response.Movie

interface DetailsMovieListener {

    fun detailsMovieOnClick(movie: Movie, position: Int,imageView: ImageView)
}