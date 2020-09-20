package com.swvl.androidtask.movieslist.model


import com.egabi.core.networking.Outcome
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.commons.data.remote.Photo
import com.swvl.androidtask.commons.data.remote.RecentResponse
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface ListDataContract {
    interface Repository {
        val moviesFetchOutcome: PublishSubject<Outcome<List<Movie>>>
        val moviesPicturesFetchOutcome: PublishSubject<Outcome<List<Photo>>>
        fun fetchMovies()
        fun searchForMovies(movietitle: String)
        fun searchForMoviesByQuery(movietitle: String)
        fun searchMoviePictures(movietitle: String)
        fun handleError(error: Throwable)
    }

    interface Local {
        fun getMoviesFromAssets(): Flowable<List<Movie>>
        fun getMoviesFromDb(): Flowable<List<Movie>>
        fun searchForMoviesByQuery(movietitle: String): Flowable<List<Movie>>
        fun saveMovies(movies: List<Movie>)
    }

    interface Remote {
        fun searchMoviePictures(title: String): Single<RecentResponse>
    }
}