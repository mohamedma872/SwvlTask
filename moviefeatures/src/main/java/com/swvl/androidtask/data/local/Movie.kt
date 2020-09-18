package com.swvl.androidtask.data.local

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Movie(
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int,
    val title: String,
    val year: Int
) : Parcelable

data class MoviesLst(
    val movies: List<Movie>
)