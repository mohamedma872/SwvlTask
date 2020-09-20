package com.swvl.androidtask.movieslist.model

import android.content.Context
import androidx.sqlite.db.SimpleSQLiteQuery
import com.egabi.core.extensions.performOnBack
import com.egabi.core.networking.Scheduler
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.commons.data.local.MoviesDb
import com.swvl.androidtask.utils.Util
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable


class ListLocalData(
    private val movieDb: MoviesDb,
    private val scheduler: Scheduler,
    private val ctx: Context
) : ListDataContract.Local {
    override fun getMoviesFromAssets(): Flowable<List<Movie>> {
        return Observable.fromArray(Util.getMovies(ctx)).performOnBack(scheduler).toFlowable(
            BackpressureStrategy.BUFFER
        )
    }

    override fun getMoviesFromDb(): Flowable<List<Movie>> {
        return movieDb.moviesDao().getMovies().performOnBack(scheduler)
    }


    override fun searchForMoviesByQuery(
        movietitle: String
    ): Flowable<List<Movie>> {


        val query = SimpleSQLiteQuery(
            """SELECT M.*
FROM Movie M
Where Id IN (SELECT M2.ID
                    FROM Movie M2
                    WHERE M2.Year = M.Year  AND  M2.title  like ? 
 Order By M2.Rating DESC
LIMIT 5
                   )   
Order By M.Year DESC, M.Rating  DESC;""", arrayOf(movietitle)
        )

        return movieDb.moviesDao().findMoviesByTitle(query)
            .performOnBack(scheduler)
    }

    override fun saveMovies(movies: List<Movie>) {
        Completable.fromAction {
            movieDb.moviesDao().addMovies(movies)
        }.performOnBack(scheduler)
            .subscribe()
    }


}