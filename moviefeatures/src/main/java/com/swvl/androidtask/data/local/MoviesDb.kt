package com.swvl.androidtask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDb : RoomDatabase() {
    abstract fun moviesDao(): MovieDao
}
