package com.butterfly.klepto.faildragon.modal.redditmodal


import com.google.gson.annotations.SerializedName

data class SecureMedia(
    @SerializedName("oembed")
    val oembed: Oembed?,
    @SerializedName("type")
    val type: String?
)