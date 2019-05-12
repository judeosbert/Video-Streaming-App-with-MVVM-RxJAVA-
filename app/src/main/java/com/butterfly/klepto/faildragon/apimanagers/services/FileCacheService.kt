package com.butterfly.klepto.faildragon.apimanagers.services

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface FileCacheService {
    @Streaming
    @GET
    fun cacheFile(@Url url:String): Call<ResponseBody>
}