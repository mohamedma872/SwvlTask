package com.swvl.androidtask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable


@Dao
interface MovieDao {
    @Query("SELECT * FROM movie ORDER BY year DESC")
    fun getMovies(): Flowable<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movies: List<Movie>): List<Long>

    @Query("SELECT * FROM movie WHERE title LIKE :title AND rating =:rating ORDER BY year DESC LIMIT :maxResults")
    fun findMoviesByTitle(title: String, maxResults: Int, rating: Int): Flowable<List<Movie>>

    @Query("SELECT * FROM movie WHERE title=:title")
    fun findMovieByTitle(title: String): Movie?
}