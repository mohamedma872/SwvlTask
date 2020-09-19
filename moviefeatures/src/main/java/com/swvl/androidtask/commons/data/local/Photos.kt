package com.swvl.androidtask.commons.data.local

import com.google.gson.annotations.SerializedName

class Photos(val photos: Data) {
    class Data(@SerializedName("photo") val pictures: List<MoviePicture>)
}