package com.udacity.asteroidradar.main

import com.squareup.moshi.Json

data class PictureOfDay(@Json(name = "media_type") val mediaType: String, val title: String,
                        val url: String)