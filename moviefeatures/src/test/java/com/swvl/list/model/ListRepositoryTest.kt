package com.karntrehan.posts.list.model


import com.egabi.core.testing.TestScheduler
import com.nhaarman.mockito_kotlin.mock
import com.swvl.androidtask.movieslist.model.ListDataContract
import com.swvl.androidtask.movieslist.model.ListRepository
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
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


}