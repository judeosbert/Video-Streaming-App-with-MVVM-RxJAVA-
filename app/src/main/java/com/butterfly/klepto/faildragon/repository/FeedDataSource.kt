package com.butterfly.klepto.faildragon.repository

import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.butterfly.klepto.faildragon.apimanagers.RedditApiManager
import com.butterfly.klepto.faildragon.apimanagers.TwitchApiManager
import com.butterfly.klepto.faildragon.modal.Feed
import com.butterfly.klepto.faildragon.modal.redditmodal.RedditTopPostsModal
import com.butterfly.klepto.faildragon.utils.FileCacher
import com.butterfly.klepto.faildragon.utils.getVideoName
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class FeedDataSource: ItemKeyedDataSource<String, Feed>(),KoinComponent {
    private val redditApiManager: RedditApiManager by inject()
    private val twitchApiManager: TwitchApiManager by inject()
    private val fileCacher: FileCacher by inject()
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Feed>) {
        Log.d("JUDE_LOAD","Initial Load")
        redditApiManager.service.getTopPosts(null)
            .flatMap{
                return@flatMap getFinalFeed(it)
            }
            .flatMap {
                return@flatMap  filterErrorObject(it)
            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(  // named arguments for lambda Subscribers
                onNext = {

                    callback.onResult(it)
                    println("Repository List${it}")
                    try {
                        fileCacher.cacheFile(it)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }




                },
                onError =  { it.printStackTrace() },
                onComplete = { }
            )

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Feed>) {
        Log.d("JUDE_LOAD","after Load")
        redditApiManager.service.getTopPosts(params.key)
            .flatMap{
                return@flatMap getFinalFeed(it)
            }
            .flatMap {
                return@flatMap  filterErrorObject(it)
            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(  // named arguments for lambda Subscribers
                onNext = {
                    callback.onResult(it)
                    println("Repository List${it}")
                    try {
                        fileCacher.cacheFile(it)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }

                },
                onError =  { it.printStackTrace() },
                onComplete = { println("Done!") }
            )
    }


    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Feed>) {
        Log.d("JUDE_LOAD","Before Load")
    }

    override fun getKey(item: Feed): String {
        Log.d("JUDE_LOAD","getKeyCalled"+item.afterKey)
        return item.afterKey?:""
        }

    private fun filterErrorObject(redditPosts: MutableList<Feed>): Observable<MutableList<Feed>> {
        return Observable.fromIterable(redditPosts)
            .filter {
                !it.isError
            }
            .toList().toObservable()


    }

    private fun getFinalFeed(redditPosts: RedditTopPostsModal): Observable<MutableList<Feed>> {
        val afterKey:String = redditPosts.data?.after?:""
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
                            child.data?.ups,
                            child.data?.id,
                            false,
                            afterKey
                        )
                        return@map feed
                    }

            }
            .onErrorReturn { Feed(isError = true) }
            .toList().toObservable()

    }
        enum class NetworkLoadStates{
            LOADING,COMPLETE
        }
}