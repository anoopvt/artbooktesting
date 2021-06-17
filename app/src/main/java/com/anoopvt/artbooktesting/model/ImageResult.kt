package com.anoopvt.artbooktesting.model

import com.google.gson.annotations.SerializedName

data class ImageResult(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val previewURL: String,
    val previewWidth: Int,
    val previewHeight: Int,
    @SerializedName("webformatURL")
    val webFormatURL: String,
    @SerializedName("webformatWidth")
    val webFormatWidth: Int,
    @SerializedName("webformatHeight")
    val webFormatHeight: Int,
    val largeImageURL: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val favorites: Int,
    val likes: Int,
    @SerializedName("user_id")
    val userId: Int,
    val user: String,
    val userImageURL: String,
)
