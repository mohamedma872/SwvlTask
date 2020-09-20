package com.swvl.androidtask.movieslist.model

import com.swvl.androidtask.commons.data.remote.FlickerService
import com.swvl.androidtask.commons.data.remote.RecentResponse
import com.swvl.androidtask.utils.Util
import io.reactivex.Single


class ListRemoteData(val service: FlickerService) : ListDataContract.Remote {


    override fun searchMoviePictures(title: String): Single<RecentResponse>? {
        return service.searchForPhotos(
            Util.method,
            Util.api_key,
            "url_l",
            Util.format,
            Util.nojsoncallback, title
        )
    }
}