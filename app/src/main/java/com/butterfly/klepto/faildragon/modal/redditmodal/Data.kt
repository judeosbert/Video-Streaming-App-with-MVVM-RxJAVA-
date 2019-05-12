package com.butterfly.klepto.faildragon.modal.redditmodal


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("after")
    val after: String?,
    @SerializedName("before")
    val before: Any?,
    @SerializedName("children")
    val children: List<Children?>?,
    @SerializedName("dist")
    val dist: Int?,
    @SerializedName("modhash")
    val modhash: String?
)