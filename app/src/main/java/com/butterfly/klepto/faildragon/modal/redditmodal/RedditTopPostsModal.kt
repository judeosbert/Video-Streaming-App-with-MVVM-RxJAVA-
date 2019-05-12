package com.butterfly.klepto.faildragon.modal.redditmodal


import com.google.gson.annotations.SerializedName

data class RedditTopPostsModal(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("kind")
    val kind: String?
)