package com.butterfly.klepto.faildragon.apimanagers

import com.butterfly.klepto.faildragon.apimanagers.services.RedditService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RedditApiManager{
private val interceptor = HttpLoggingInterceptor()
    private val  okHttpClient: OkHttpClient
    private val retrofit:Retrofit
    val service: RedditService
    init{
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://www.reddit.com/r/LivestreamFail/")
            .build()

        service = retrofit.create(RedditService::class.java)
    }

}