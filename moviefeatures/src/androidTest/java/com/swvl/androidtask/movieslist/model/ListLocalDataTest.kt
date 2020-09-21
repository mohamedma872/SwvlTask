package com.swvl.androidtask.movieslist.model

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.egabi.core.testing.TestScheduler
import com.swvl.androidtask.commons.data.local.MovieDao
import com.swvl.androidtask.commons.data.local.MoviesDb
import com.swvl.androidtask.commons.testing.DummyData
import org.junit.*
import java.io.IOException

internal class ListLocalDataTest {

    private lateinit var movieDao: MovieDao
    private lateinit var movieDatabase: MoviesDb
    private lateinit var listLocalData: ListLocalData
    private val context: Context = ApplicationProvider.getApplicationContext()

    //Necessary for Room insertions to work
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        //instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        movieDatabase = Room.inMemoryDatabaseBuilder(context, MoviesDb::class.java).build()
        movieDao = movieDatabase.moviesDao()
        listLocalData = ListLocalData(
            movieDatabase,
            TestScheduler(),
            InstrumentationRegistry.getInstrumentation().targetContext
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDB() = movieDatabase.close()


    @Test
    fun getMoviesFromAssets() {
        listLocalData.getMoviesFromAssets().test().run {
            assertNoErrors()
            assertValueCount(1)
            Assert.assertEquals(values()[0].size, 2272)
        }

    }

    @Test
    fun getMoviesFromDb() {
        val movies = DummyData.createMoviesList(30)
        movieDao.addMovies(movies)
        listLocalData.getMoviesFromDb().test().run {
            assertNoErrors()
            assertValueCount(1)
            Assert.assertEquals(values()[0].size, 30)
        }
    }

    @Test
    fun searchForMoviesByQuery() {
        val movies = DummyData.createMoviesList(20)
        val movieTitle = "Movie"
        movieDao.addMovies(movies)
        listLocalData.searchForMoviesByQuery("%$movieTitle%").test().run {
            assertNoErrors()
            assertValueCount(1)
            Assert.assertEquals(values()[0].size, 20)
        }
    }

    @Test
    fun saveMovies() {
        val movies = DummyData.createMoviesList(10)
        val movieTitle = "Movie"
        movieDao.addMovies(movies)
        listLocalData.searchForMoviesByQuery("%$movieTitle%").test().run {
            assertNoErrors()
            assertValueCount(1)
            Assert.assertEquals(values()[0].size, 10)
        }
    }
}