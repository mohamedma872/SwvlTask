package com.swvl.androidtask.movieslist.model

import com.egabi.core.extensions.*
import com.egabi.core.networking.Outcome
import com.egabi.core.networking.Scheduler
import com.swvl.androidtask.data.local.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject


class ListRepository(
    private val local: ListDataContract.Local,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : ListDataContract.Repository {
    override val moviesFetchOutcome: PublishSubject<Outcome<List<Movie>>> =
        PublishSubject.create()


    override fun fetchMovies() {

        moviesFetchOutcome.loading(true)
        //Observe changes to the db
        local.getMoviesFromDb()
            .performOnBackOutOnMain(scheduler)
            .subscribe({ movies ->
                if (movies.isEmpty()) {
                    local.getMoviesFromAssets().subscribe({ assetsMovies ->
                        local.saveMovies(assetsMovies)
                        moviesFetchOutcome.success(movies)
                    }, { error -> handleError(error) })
                } else {
                    moviesFetchOutcome.success(movies)
                }

            }, { error -> handleError(error) })
            .addTo(compositeDisposable)
    }

    override fun searchForMovies(movietitle: String) {
        moviesFetchOutcome.loading(true)
        //Observe changes to the db
        local.searchForMovies(movietitle)
            .performOnBackOutOnMain(scheduler)
            .subscribe({ movies ->
                moviesFetchOutcome.success(movies)
            }, { error -> handleError(error) })
            .addTo(compositeDisposable)
    }

    override fun handleError(error: Throwable) {
        moviesFetchOutcome.failed(error)
    }


}