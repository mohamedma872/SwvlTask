package com.swvl.androidtask.movieslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.egabi.core.extensions.toLiveData
import com.egabi.core.networking.Outcome
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.movieslist.model.ListDataContract
import io.reactivex.disposables.CompositeDisposable

class ListViewModel(
    private val repo: ListDataContract.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val moviesOutcome: LiveData<Outcome<List<Movie>>> by lazy {
        //Convert publish subject to livedata
        repo.moviesFetchOutcome.toLiveData(compositeDisposable)
    }

    fun getMoviesList() {
        if (moviesOutcome.value == null)
            repo.fetchMovies()
    }


    override fun onCleared() {
        super.onCleared()
        //clear the disposables when the viewmodel is cleared
        compositeDisposable.clear()
        //PostDH.destroyListComponent()
    }
}