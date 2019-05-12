package com.butterfly.klepto.faildragon.apimanagers.services

import com.butterfly.klepto.faildragon.modal.twitchmodal.TwitchLinksModal
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TwitchService {
    @GET("{videoName}/status")
    fun getVideoDownloadLinks(@Path("videoName") videoName:String):Observable<TwitchLinksModal>
}