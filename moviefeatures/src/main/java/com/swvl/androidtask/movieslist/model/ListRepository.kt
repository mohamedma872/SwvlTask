package com.swvl.androidtask.movieslist.model

import com.egabi.core.extensions.*
import com.egabi.core.networking.Outcome
import com.egabi.core.networking.Scheduler
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.commons.data.remote.Photo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
class ListRepository(
    private val local: ListDataContract.Local,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable,
    private val remote: ListDataContract.Remote
) : ListDataContract.Repository {
    override val moviesFetchOutcome: PublishSubject<Outcome<List<Movie>>> =
        PublishSubject.create()
    override val moviesPicturesFetchOutcome: PublishSubject<Outcome<List<Photo>>> =
        PublishSubject.create()


    override fun fetchMovies() {

        moviesFetchOutcome.loading(true)
        //Observe changes to the db
        local.getMoviesFromDb()
            .performOnBackOutOnMain(scheduler)
            .subscribe({ movies ->
                if (movies.isEmpty()) {
                    local.getMoviesFromAssets().performOnBackOutOnMain(scheduler)
                        .subscribe({ assetsMovies ->
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
        local.getMoviesFromDb()
            .performOnBackOutOnMain(scheduler)
            .subscribe({ movies ->
                val filtered = movies
                    .filter { it.title.toLowerCase().contains(movietitle.toLowerCase()) }
                    .sortedWith(compareByDescending({ it.year }, { it.rating }))
                    .groupBy { t -> t.year }
                    .flatMap { (_, lst) -> lst.take(5) }
                moviesFetchOutcome.success(filtered)
            }, { error -> handleError(error) })
            .addTo(compositeDisposable)
    }

    override fun searchForMoviesByQuery(movietitle: String) {
        moviesFetchOutcome.loading(true)
        //Observe changes to the db
        local.searchForMoviesByQuery("%$movietitle%")
            .performOnBackOutOnMain(scheduler)
            .subscribe({ movies ->

                moviesFetchOutcome.success(movies)
            }, { error -> handleError(error) })
            .addTo(compositeDisposable)
    }

    override fun searchMoviePictures(movietitle: String) {
        moviesFetchOutcome.loading(true)
        //Observe changes to the db
        remote.searchMoviePictures(movietitle)
            .performOnBackOutOnMain(scheduler)
            .subscribe({ movies ->
                moviesPicturesFetchOutcome.success(movies.photos.photo)
            }, { error -> handleError(error) })
            .addTo(compositeDisposable)
    }

    override fun handleError(error: Throwable) {
        moviesFetchOutcome.failed(error)
    }


}

fun <T> compareByDescending(vararg selectors: (T) -> Comparable<*>?): Comparator<T> {
    return Comparator { b, a -> compareValuesBy(a, b, *selectors) }
}