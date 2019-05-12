package com.butterfly.klepto.faildragon.modal.redditmodal


import com.google.gson.annotations.SerializedName

data class Oembed(
    @SerializedName("author_name")
    val authorName: String?,
    @SerializedName("author_url")
    val authorUrl: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("html")
    val html: String?,
    @SerializedName("provider_name")
    val providerName: String?,
    @SerializedName("provider_url")
    val providerUrl: String?,
    @SerializedName("thumbnail_height")
    val thumbnailHeight: Int?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("thumbnail_width")
    val thumbnailWidth: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("version")
    val version: String?,
    @SerializedName("width")
    val width: Int?
)