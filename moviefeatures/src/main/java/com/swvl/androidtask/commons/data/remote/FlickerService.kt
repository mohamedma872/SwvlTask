package com.swvl.androidtask.commons.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerService {

    @GET("rest")
    fun searchForPhotos(
        @Query("method") method: String,
        @Query("api_key") apikey: String,
        @Query("extras") extras: String,
        @Query("format") format: String,
        @Query("nojsoncallback") set: String,
        @Query("text") s: String
    ): Single<RecentResponse>

}