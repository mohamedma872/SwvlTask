package com.swvl.androidtask.commons.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    val cast: ArrayList<String>,
    val genres: ArrayList<String>,
    val rating: Int,
    val title: String,
    val year: Int, @PrimaryKey(autoGenerate = true) val id: Int
)

data class MoviesLst(
    val movies: List<Movie>
)