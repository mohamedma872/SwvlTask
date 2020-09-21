package com.swvl.androidtask.commons.data.remote

class Photo(
    private val id: String, private var url_l: String? = null, private val secret: String,
    private val server: String,
    private val farm: Int
) {
    /**
     * @return image url based on above info.
     */
    fun getImageURL() = "https://farm$farm.static.flickr.com/$server/${id}_$secret.jpg"
}