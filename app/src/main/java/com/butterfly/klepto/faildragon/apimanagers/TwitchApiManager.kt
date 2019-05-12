package com.butterfly.klepto.faildragon.apimanagers

import com.butterfly.klepto.faildragon.apimanagers.services.TwitchService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TwitchApiManager {
    private val interceptor = HttpLoggingInterceptor()
    private val  okHttpClient:OkHttpClient
    private val retrofit:Retrofit
    val service: TwitchService
    init{
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://clips.twitch.tv/api/v2/clips/")
            .build()

        service = retrofit.create(TwitchService::class.java)
    }


}