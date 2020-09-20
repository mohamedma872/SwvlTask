package com.swvl.androidtask.commons.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Movie::class], version = 2, exportSchema = false)
@TypeConverters(com.swvl.androidtask.commons.data.local.TypeConverters::class)
abstract class MoviesDb : RoomDatabase() {
    abstract fun moviesDao(): MovieDao
}
