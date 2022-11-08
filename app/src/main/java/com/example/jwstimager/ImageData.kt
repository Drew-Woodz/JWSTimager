package com.example.jwstimager

data class ImageData(
    val title: String,
    val src_link: String,
    val rsc_id: Int,
    var isFavorite: Boolean = false
    )