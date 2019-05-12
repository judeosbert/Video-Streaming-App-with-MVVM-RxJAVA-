package com.butterfly.klepto.faildragon.modal.twitchmodal


import com.google.gson.annotations.SerializedName

data class QualityOption(
    @SerializedName("frame_rate")
    val frameRate: Int?,
    @SerializedName("quality")
    val quality: String?,
    @SerializedName("source")
    val source: String?
)