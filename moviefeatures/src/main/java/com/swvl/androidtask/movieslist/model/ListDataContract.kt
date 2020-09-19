package com.swvl.androidtask.movieslist.model


import com.egabi.core.networking.Outcome
import com.swvl.androidtask.commons.data.local.Movie
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

interface ListDataContract {
    interface Repository {
        val moviesFetchOutcome: PublishSubject<Outcome<List<Movie>>>
        fun fetchMovies()
        fun searchForMovies(movietitle: String)
        fun handleError(error: Throwable)
    }

    interface Local {
        fun getMoviesFromAssets(): Flowable<List<Movie>>
        fun getMoviesFromDb(): Flowable<List<Movie>>
        fun searchForMovies(movietitle: String): Flowable<List<Movie>>
        fun saveMovies(movies: List<Movie>)
    }

}