package com.zup.appkoin.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zup.appkoin.api.response.GetMoviesResponse
import com.zup.appkoin.api.response.Movie
import com.zup.appkoin.repository.DataRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.coroutineContext

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @RelaxedMockK
    lateinit var dataRepository: DataRepository

    @InjectMockKs
    lateinit var viewModel: MainViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var moviesResponse: GetMoviesResponse

    @RelaxedMockK
    lateinit var listMovies: List<Movie>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_initializeLaunch() = runBlockingTest {
        // GIVEN  = dado que tal coisa
        val expectedPageLaunch = 1
        coEvery { dataRepository.getPopularMovies(expectedPageLaunch) } returns moviesResponse
        coEvery { dataRepository.getTopRatedMovies(expectedPageLaunch) } returns moviesResponse
        coEvery { dataRepository.getUpcomingMovies(expectedPageLaunch) } returns moviesResponse
        every { moviesResponse.movies } returns listMovies

        // WHEN = quando
        viewModel.initializeLaunch()

        // THEN =  entao
        assertEquals(listMovies, viewModel.popularMovie.value)
        assertEquals(listMovies, viewModel.ratedMovies.value)
        assertEquals(listMovies, viewModel.upcomingMovies.value)
        coVerify(exactly = 1) { dataRepository.getPopularMovies(expectedPageLaunch) }
        coVerify(exactly = 1) { dataRepository.getTopRatedMovies(expectedPageLaunch) }
        coVerify(exactly = 1) { dataRepository.getUpcomingMovies(expectedPageLaunch) }
    }

    @Test
    fun test_initializeAsync() = runBlockingTest {
        // GIVEN
        val expectedPageAsync = 1
        coEvery { dataRepository.getPopularMovies(expectedPageAsync) } returns moviesResponse
        coEvery { dataRepository.getTopRatedMovies(expectedPageAsync) } returns moviesResponse
        coEvery { dataRepository.getUpcomingMovies(expectedPageAsync) } returns moviesResponse
        every { moviesResponse.movies } returns listMovies
        // WHEN

        viewModel.initializeAsync()
        // THEN

        assertEquals(listMovies, viewModel.popularMovie.value)
        assertEquals(listMovies, viewModel.ratedMovies.value)
        assertEquals(listMovies, viewModel.upcomingMovies.value)
        coVerify(exactly = 1) { dataRepository.getPopularMovies(expectedPageAsync) }
        coVerify(exactly = 1) { dataRepository.getTopRatedMovies(expectedPageAsync) }
        coVerify(exactly = 1) { dataRepository.getUpcomingMovies(expectedPageAsync) }
    }

    @Test
    fun testFailureInitializeLaunch() = runBlockingTest {
        // GIVEN  = dado que tal coisa
        val error = Throwable()
        val expectedPage = 1
        coEvery { dataRepository.getPopularMovies(expectedPage) } throws error
        coEvery { dataRepository.getTopRatedMovies(expectedPage) } throws error
        coEvery { dataRepository.getUpcomingMovies(expectedPage) } throws error
        every { moviesResponse.movies } returns listMovies
        // WHEN = quando
        viewModel.initializeLaunch()

        // THEN =  entao
        assertEquals(error, viewModel.movieError.value)
        assertEquals(error, viewModel.movieError.value)
        assertEquals(error, viewModel.movieError.value)
    }


//    @Test
//    fun testFailureInitializeAsync() = runBlockingTest {
//        // GIVEN  = dado que tal coisa
//        val error = MyException("error")
//        val expectedPage = 1
//        coEvery { dataRepository.getPopularMovies(expectedPage) } throws error
//        coEvery { dataRepository.getTopRatedMovies(expectedPage) } throws error
//        coEvery { dataRepository.getUpcomingMovies(expectedPage) } throws error
//        every { moviesResponse.movies } returns listMovies
//
//        // WHEN = quando
//        viewModel.initializeAsync()
//
//        // THEN =  entao
//        assertEquals(error, viewModel.movieError.value)
//
//    }
//
//    class MyException(override val message: String) : Throwable(message) {
//        override fun equals(other: Any?): Boolean {
//            if (this === other) return true
//            if (javaClass != other?.javaClass) return false
//
//            other as MyException
//
//            if (message != other.message) return false
//
//            return true
//        }
//
//        override fun hashCode(): Int {
//            return message.hashCode()
//        }
//    }
}


