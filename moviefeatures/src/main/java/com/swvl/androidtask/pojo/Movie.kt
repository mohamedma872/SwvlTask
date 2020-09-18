package com.swvl.androidtask.pojo

data class Movie(
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int,
    val title: String,
    val year: Int
)
data class MoviesLst(
    val movies: List<Movie>
)