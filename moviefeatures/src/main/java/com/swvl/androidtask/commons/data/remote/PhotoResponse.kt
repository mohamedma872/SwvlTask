package com.swvl.androidtask.commons.data.remote


data class PhotoResponse(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: List<Photo>
)