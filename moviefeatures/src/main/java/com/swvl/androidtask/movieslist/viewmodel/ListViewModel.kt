package com.swvl.androidtask.movieslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.egabi.core.extensions.toLiveData
import com.egabi.core.networking.Outcome
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.commons.data.remote.Photo
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
    val picturesOutcome: LiveData<Outcome<List<Photo>>> by lazy {
        //Convert publish subject to livedata
        repo.moviesPicturesFetchOutcome.toLiveData(compositeDisposable)
    }

    fun getMoviesList() {
        if (moviesOutcome.value == null)
            repo.fetchMovies()
    }

    fun searchForMovies(tittle: String) {
        if (tittle.isEmpty()) {
            repo.fetchMovies()
        } else {
            repo.searchForMovies(tittle)
            //repo.searchForMoviesByQuery(tittle)
        }

    }

    fun searchForPohotos(tittle: String) {
        repo.searchMoviePictures(tittle)
    }

    override fun onCleared() {
        super.onCleared()
        //clear the disposables when the viewmodel is cleared
        compositeDisposable.clear()
        //PostDH.destroyListComponent()
    }
}