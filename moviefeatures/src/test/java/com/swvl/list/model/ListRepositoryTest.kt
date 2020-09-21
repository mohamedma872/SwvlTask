package com.swvl.list.model


import com.egabi.core.networking.Outcome
import com.egabi.core.testing.TestScheduler
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.commons.testing.DummyData
import com.swvl.androidtask.movieslist.model.ListDataContract
import com.swvl.androidtask.movieslist.model.ListRepository
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for [ListRepository]
 * */
@RunWith(RobolectricTestRunner::class)
class ListRepositoryTest {
    private val local: ListDataContract.Local = mock()
    private val remote: ListDataContract.Remote = mock()

    private lateinit var repository: ListRepository
    private val compositeDisposable = CompositeDisposable()

    @Before
    fun init() {
        repository = ListRepository(local, TestScheduler(), compositeDisposable, remote)
    }

    /**
     * Verify if calling [ListRepository.fetchMovies] triggers [ListDataContract.Local.getMoviesFromDb]
     *  and it's result is added to the [ListRepository.moviesFetchOutcome]
     * */
    @Test
    fun fetchMovies() {
        //declare dummy data
        val movies = DummyData.createMoviesList(20)
        //return dummy data when call fetch database
        whenever(local.getMoviesFromDb()).doReturn(Flowable.just(movies))

        val obs = TestObserver<Outcome<List<Movie>>>()

        repository.moviesFetchOutcome.subscribe(obs)
        obs.assertEmpty()

        repository.fetchMovies()
        verify(local).getMoviesFromDb()

        obs.assertValueAt(0, Outcome.loading(true))
        obs.assertValueAt(1, Outcome.loading(false))
        obs.assertValueAt(2, Outcome.success(movies))
    }

}