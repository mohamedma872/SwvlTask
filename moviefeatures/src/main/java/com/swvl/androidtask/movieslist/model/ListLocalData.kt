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

    override fun searchForMovies(
        movietitle: String
    ): Flowable<List<Movie>> {
        val query = SimpleSQLiteQuery(
            "WITH TOPTEN AS (\n" +
                    "    SELECT *, ROW_NUMBER() \n" +
                    "    over (\n" +
                    "        PARTITION BY Movie.year\n" +
                    "        order by Movie.year , Movie.rating DESC\n" +
                    "    ) AS RowNo \n" +
                    "    FROM Movie\n" +
                    ")\n" +
                    "SELECT * FROM TOPTEN WHERE RowNo <= 5  and  TOPTEN.title like ? order by TOPTEN.year  DESC",
            arrayOf(movietitle)
        )

        return Flowable.fromArray(movieDb.moviesDao().findMoviesByTitle(query))
            .performOnBack(scheduler)
    }

    override fun saveMovies(movies: List<Movie>) {
        Completable.fromAction {
            movieDb.moviesDao().addMovies(movies)
        }.performOnBack(scheduler)
            .subscribe()
    }


}