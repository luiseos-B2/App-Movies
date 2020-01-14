package com.zup.appkoin.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.zup.appkoin.R
import com.zup.appkoin.api.response.Movie
import com.zup.appkoin.view.adapter.listeners.DetailsMovieListener

class MoviesRecycler : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: List<Movie> = ArrayList()
    var detailsMovieListener: DetailsMovieListener?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = this.movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.poster.setOnClickListener {
            detailsMovieListener?.detailsMovieOnClick(movies[position], position,holder.poster)


        }
    }

    fun setList(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

    fun bind(movie: Movie) {
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
            .transform(CenterCrop())
            .into(poster)
    }
}