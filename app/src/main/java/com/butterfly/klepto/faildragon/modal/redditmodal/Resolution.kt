package com.butterfly.klepto.faildragon.modal.redditmodal


import com.google.gson.annotations.SerializedName

data class Resolution(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)