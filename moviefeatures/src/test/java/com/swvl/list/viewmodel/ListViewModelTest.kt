package com.swvl.list.viewmodel

import androidx.lifecycle.Observer
import com.egabi.core.networking.Outcome
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.movieslist.model.ListDataContract
import com.swvl.androidtask.movieslist.viewmodel.ListViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.junit.Before
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

    @Before
    fun init() {
        viewModel = ListViewModel(repo, CompositeDisposable())
        whenever(repo.moviesFetchOutcome).doReturn(PublishSubject.create())
        viewModel.moviesOutcome.observeForever(outcome)
    }


}