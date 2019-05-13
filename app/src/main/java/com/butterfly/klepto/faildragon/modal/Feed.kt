package com.butterfly.klepto.faildragon.modal

data class Feed (
    val videoUrl:String?="",
    val postOwner:String?="",
    val postTitle:String?="",
    val upVotes:Int?=0,
    val id:String?="",
    val isError:Boolean = false,
    val afterKey:String?=""

)
