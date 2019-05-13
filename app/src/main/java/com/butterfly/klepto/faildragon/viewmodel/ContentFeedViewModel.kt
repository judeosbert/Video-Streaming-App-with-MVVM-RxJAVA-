package com.butterfly.klepto.faildragon.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.butterfly.klepto.faildragon.modal.Feed
import com.butterfly.klepto.faildragon.repository.FeedDataSourceFactory

class ContentFeedViewModel:ViewModel(){
    var feedPagedList:LiveData<PagedList<Feed>>
    var liveDataSource:LiveData<ItemKeyedDataSource<String,Feed>>

    init {
        val feedDataSourceFactory = FeedDataSourceFactory()
        liveDataSource = feedDataSourceFactory.getFeedLiveDataSource()

        val pagedListConfig =  (PagedList.Config.Builder())
                                .setPrefetchDistance(10)
                                .setPageSize(50)
                                .setEnablePlaceholders(false).build()
        feedPagedList = LivePagedListBuilder(feedDataSourceFactory,pagedListConfig).build()
    }

}