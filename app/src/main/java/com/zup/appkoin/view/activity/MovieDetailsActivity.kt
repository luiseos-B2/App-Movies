package com.zup.appkoin.view.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.textview.MaterialTextView
import com.zup.appkoin.R
import kotlinx.android.synthetic.main.activity_movie_details.*



class MovieDetailsActivity : AppCompatActivity() {

    companion object{
        const val MOVIE_BACKDROP = "extra_movie_backdrop"
        const val MOVIE_POSTER = "extra_movie_poster"
        const val MOVIE_TITLE = "extra_movie_title"
        const val MOVIE_RATING = "extra_movie_rating"
        const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
        const val MOVIE_OVERVIEW = "extra_movie_overview"
    }

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: MaterialTextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: MaterialTextView
    private lateinit var overview: MaterialTextView
    private lateinit var progressBackdrop: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        progressBackdrop = findViewById(R.id.progress_backdrop)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        ib_come_back.setOnClickListener { onBackPressed() }

    }

    private fun populateDetails(extras: Bundle) {

        val movieBackdrop: String? = extras.getString(MOVIE_BACKDROP)
        if (movieBackdrop == null) {
            progressBackdrop.visibility = View.GONE
            mtv_image_not_found.visibility  = View.VISIBLE
        } else {
            progressBackdrop.visibility = View.GONE
            backdrop.visibility = View.VISIBLE
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$movieBackdrop")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
    }

}
