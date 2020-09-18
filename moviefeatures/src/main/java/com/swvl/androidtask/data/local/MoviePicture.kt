package com.swvl.androidtask.data.local

class MoviePicture(
    private val id: String,
    private val secret: String,
    private val server: String,
    private val farm: Int,
    val title: String
) {
    /**
     * @return image url based on above info.
     */
    fun getImageURL() = "https://farm$farm.static.flickr.com/$server/${id}_$secret.jpg"
}