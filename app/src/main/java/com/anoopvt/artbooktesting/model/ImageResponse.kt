package com.anoopvt.artbooktesting.model

data class ImageResponse(
    val hits:ArrayList<ImageResult>,
    val total:Int,
    val totalHits:Int
)
