package com.egabi.core.constants


object Constants {

    const val API_URL = ""
    var language = "EN"

    object Movies {
        val DB_NAME = "posts_db"
    }

    val langCode: String
        inline get() {
            return if (language == "EN") {
                "En"
            } else {
                "Ar"
            }
        }

}

