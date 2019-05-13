package com.butterfly.klepto.faildragon.apimanagers.services

import com.butterfly.klepto.faildragon.modal.redditmodal.RedditTopPostsModal
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
    @GET("hot.json")
    fun getTopPosts(@Query("after") after:String?
        ,@Query("limit") limit:Int = 5
    ):Observable<RedditTopPostsModal>
}