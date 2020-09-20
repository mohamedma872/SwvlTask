package com.swvl.androidtask.commons.data.local

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Flowable


@Dao
interface MovieDao {
    @Query("SELECT * FROM movie order by  year DESC , rating  DESC")
    fun getMovies(): Flowable<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movies: List<Movie>): List<Long>

    @RawQuery(observedEntities = [Movie::class])
    fun findMoviesByTitle(query: SupportSQLiteQuery?): Flowable<List<Movie>>


    @Query("SELECT * FROM movie WHERE title=:title")
    fun findMovieByTitle(title: String): Movie?
}