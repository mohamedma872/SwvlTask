package com.swvl.list.viewmodel

import androidx.lifecycle.Observer
import com.egabi.core.networking.Outcome
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.commons.data.remote.Photo
import com.swvl.androidtask.commons.testing.DummyData
import com.swvl.androidtask.movieslist.model.ListDataContract
import com.swvl.androidtask.movieslist.viewmodel.ListViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for [ListViewModel]
 * */
@RunWith(RobolectricTestRunner::class)
class ListViewModelTest {

    private lateinit var viewModel: ListViewModel

    private val repo: ListDataContract.Repository = mock()

    private val outcome: Observer<Outcome<List<Movie>>> = mock()

    private val outcomepic: Observer<Outcome<List<Photo>>> = mock()

    @Before
    fun init() {
        viewModel = ListViewModel(repo, CompositeDisposable())

        whenever(repo.moviesFetchOutcome).doReturn(PublishSubject.create())
        viewModel.moviesOutcome.observeForever(outcome)

        whenever(repo.moviesPicturesFetchOutcome).doReturn(PublishSubject.create())
        viewModel.picturesOutcome.observeForever(outcomepic)
    }

    /**
     * Test [ListViewModel.getMoviesList] triggers [ListDataContract.Repository.fetchMovies] &
     * livedata [ListViewModel.moviesOutcome] gets outcomes pushed
     * from [ListDataContract.Repository.moviesFetchOutcome]
     * */
    @Test
    fun getMoviesList() {
        viewModel.getMoviesList()
        verify(repo).fetchMovies()

        repo.moviesFetchOutcome.onNext(Outcome.loading(true))
        verify(outcome).onChanged(Outcome.loading(true))

        repo.moviesFetchOutcome.onNext(Outcome.loading(false))
        verify(outcome).onChanged(Outcome.loading(false))

        val data = DummyData.createMoviesList(4)
        repo.moviesFetchOutcome.onNext(Outcome.success(data))
        verify(outcome).onChanged(Outcome.success(data))
    }

    /**
     * Test [ListViewModel.searchForMovies] triggers [ListDataContract.Repository.searchForMovies] &
     * livedata [ListViewModel.moviesOutcome] gets outcomes pushed
     * from [ListDataContract.Repository.moviesFetchOutcome]
     * */

    @Test
    fun searchForMovies() {
        val movietittle = "the"
        viewModel.searchForMovies(movietittle)
        verify(repo).searchForMovies(movietittle)

        repo.moviesFetchOutcome.onNext(Outcome.loading(true))
        verify(outcome).onChanged(Outcome.loading(true))

        repo.moviesFetchOutcome.onNext(Outcome.loading(false))
        verify(outcome).onChanged(Outcome.loading(false))

        val data = DummyData.createMoviesList(4)
        repo.moviesFetchOutcome.onNext(Outcome.success(data))
        verify(outcome).onChanged(Outcome.success(data))
    }

    /**
     * Test [ListViewModel.searchForPohotos] triggers [ListDataContract.Repository.searchMoviePictures] &
     * livedata [ListViewModel.picturesOutcome] gets outcomes pushed
     * from [ListDataContract.Repository.moviesPicturesFetchOutcome]
     * */

    @Test
    fun searchForPohotos() {
        val movietittle = "the"
        viewModel.searchForPohotos(movietittle)
        verify(repo).searchMoviePictures(movietittle)

        repo.moviesPicturesFetchOutcome.onNext(Outcome.loading(true))
        verify(outcomepic).onChanged(Outcome.loading(true))

        repo.moviesPicturesFetchOutcome.onNext(Outcome.loading(false))
        verify(outcomepic).onChanged(Outcome.loading(false))


    }
}