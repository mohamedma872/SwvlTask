package com.swvl.androidtask.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.commons.data.local.MoviesLst
import java.io.IOException

//Singleton class where a single instance
//package-level functions
class Util private constructor() {

    companion object {
        private lateinit var instance: Util

        var method = "flickr.photos.search"
        var api_key = "52cdd04ecd2686efacbd43e6d38ea24e"
        var format = "json"
        var nojsoncallback = "1"
        var page = 1
        var per_page = 10

        val managerInstance: Util
            get() {
                return instance
            }

        fun getMovies(context: Context): List<Movie> {
            val jsonFileString = getJsonDataFromAsset(context)
            Log.i("data", jsonFileString + "")

            val gson = Gson()
            val listMoviesType = object : TypeToken<MoviesLst>() {}.type

            val listMovies: MoviesLst = gson.fromJson(jsonFileString, listMoviesType)
            listMovies.movies.forEachIndexed { idx, movie -> Log.i("data", "> Item $idx:\n$movie") }
            return listMovies.movies
        }

        private fun getJsonDataFromAsset(context: Context): String? {
            val jsonString: String
            try {
                jsonString =
                    context.assets.open("movies.json").bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString
        }
    }

}