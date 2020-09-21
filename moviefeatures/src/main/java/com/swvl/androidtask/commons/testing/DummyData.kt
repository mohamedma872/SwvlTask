package com.swvl.androidtask.commons.testing

import androidx.annotation.VisibleForTesting
import com.swvl.androidtask.commons.data.local.Movie


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DummyData {

    fun createMovie(id: Int, title: String) = Movie(
        arrayListOf("RDJ", "Scarlett Johannson"),
        arrayListOf("Sci fi", "Action"),
        5,
        title,
        2012,
        id
    )

    fun createMoviesList(movieCount: Int): List<Movie> {
        val list = ArrayList<Movie>(movieCount)
        for (i in 1..movieCount)
            list.add(
                Movie(
                    arrayListOf("RDJ", "Scarlett Johannson"),
                    arrayListOf("Sci fi", "Action"),
                    5,
                    "Movie $i",
                    201 + i,
                    i
                )
            )

        return list
    }

}