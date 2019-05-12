package com.butterfly.klepto.faildragon.apimanagers

import com.butterfly.klepto.faildragon.apimanagers.services.FileCacheService
import com.butterfly.klepto.faildragon.apimanagers.services.RedditService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FileCacheApiManager {
    private val interceptor = HttpLoggingInterceptor()
    private val  okHttpClient: OkHttpClient
    private val retrofit: Retrofit
    val service: FileCacheService
    init{
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https:www.example.com/")
            .build()

        service = retrofit.create(FileCacheService::class.java)
    }
}