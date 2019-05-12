package com.butterfly.klepto.faildragon.modal.twitchmodal


import com.google.gson.annotations.SerializedName

data class TwitchLinksModal(
    @SerializedName("quality_options")
    val qualityOptions: List<QualityOption?>?,
    @SerializedName("status")
    val status: String?
)