package com.zup.appkoin

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.zup.appkoin.view.activity.MainActivity
import com.zup.appkoin.view.adapter.MovieViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    /**
     * Test list Popular Movies
     */
    @Test
    fun popularMoviesRecycler_isDisplayed() {
        activityRule.launchActivity(Intent())
        onView(withId(R.id.popular_movies)).check(
            matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun testClickMovieListsPopularMovies() {
        onView(withId(R.id.popular_movies))
            .perform(actionOnItemAtPosition<MovieViewHolder>(17, click()))

    }

    @Test
    fun checksActivityChangePopularMovies() {
        val resultData = Intent()
        Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)

        onView(withId(R.id.popular_movies))
            .perform(actionOnItemAtPosition<MovieViewHolder>(17, click()))

    }

    /**
     * Test list Top Rated Movies
     */
    @Test
    fun topRatedMoviesRecycler_isDisplayed() {
        activityRule.launchActivity(Intent())
        onView(withId(R.id.top_rated_movies)).check(
            matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun testClickMovieListsTopRatedMovies() {
        val teste = onView(withId(R.id.top_rated_movies))
            .perform(actionOnItemAtPosition<MovieViewHolder>(10, click()))

    }

    @Test
    fun checksActivityChangeTopRatedMovies() {
        val resultData = Intent()
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)

        val teste = onView(withId(R.id.top_rated_movies))
            .perform(actionOnItemAtPosition<MovieViewHolder>(10, click()))

    }

    /**
     * Test list Upcoming Movies
     */
    @Test
    fun upcomingMoviesRecycler_isDisplayed() {
        activityRule.launchActivity(Intent())
        onView(withId(R.id.upcoming_movies)).check(
            matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun testClickMovieListsUpcomingMovies() {
        val teste = onView(withId(R.id.upcoming_movies))
            .perform(actionOnItemAtPosition<MovieViewHolder>(5, click()))

    }

    @Test
    fun checksActivityChangeUpcomingMovies() {
        val resultData = Intent()
        Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)

        onView(withId(R.id.upcoming_movies))
            .perform(actionOnItemAtPosition<MovieViewHolder>(5, click()))

    }

    @Test
    fun testSnackBarText() {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Você está offline agora.")))

    }
}
