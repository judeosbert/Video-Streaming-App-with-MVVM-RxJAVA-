package com.butterfly.klepto.faildragon.repository

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.butterfly.klepto.faildragon.apimanagers.RedditApiManager
import com.butterfly.klepto.faildragon.apimanagers.TwitchApiManager
import com.butterfly.klepto.faildragon.modal.Feed
import com.butterfly.klepto.faildragon.modal.redditmodal.RedditTopPostsModal
import com.butterfly.klepto.faildragon.utils.getVideoName
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class ContentFeedRespository:KoinComponent {
    var feed: MutableLiveData<MutableList<Feed>> = MutableLiveData()
    private val redditApiManager:RedditApiManager by inject()
    private val twitchApiManager:TwitchApiManager by inject()
    private var after:String?=null
    @SuppressLint("CheckResult")
    fun getTopPosts():MutableLiveData<MutableList<Feed>>{
        Log.d("JUDE","After"+after)
        redditApiManager.service.getTopPosts(after)
            .flatMap{
                    this.after = it.data?.after
                    return@flatMap getFinalFeed(it)
            }
            .flatMap {
                return@flatMap  filterErrorObject(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(  // named arguments for lambda Subscribers
                onNext = {
                    feed.postValue(it)
                    println("Repository List${it}")
                },
                onError =  { it.printStackTrace() },
                onComplete = { println("Done!") }
            )
        return feed
    }

    private fun filterErrorObject(redditPosts: MutableList<Feed>): Observable<MutableList<Feed>> {
        return Observable.fromIterable(redditPosts)
            .filter {
                !it.isError
            }
            .toList().toObservable()


    }

    private fun getFinalFeed(redditPosts: RedditTopPostsModal): Observable<MutableList<Feed>> {
        return Observable.fromIterable(redditPosts.data?.children)
            .filter {
                it.data?.domain.equals("clips.twitch.tv") && it.data?.postHint.equals("rich:video")
            }
            .flatMap {child->
                val videoName = getVideoName(child.data?.url?:"")
                    twitchApiManager.service.getVideoDownloadLinks(videoName)
                        .map {
                            val feed = Feed(it.qualityOptions?.get(0)?.source,
                                child.data?.author,
                                child.data?.title,
                                child.data?.ups
                            )
                            return@map feed
                        }

            }
            .onErrorReturn { Feed(isError = true) }
            .toList().toObservable()

    }




}