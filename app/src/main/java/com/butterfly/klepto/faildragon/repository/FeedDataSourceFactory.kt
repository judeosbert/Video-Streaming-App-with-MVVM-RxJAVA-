package com.butterfly.klepto.faildragon.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.butterfly.klepto.faildragon.modal.Feed


class FeedDataSourceFactory:DataSource.Factory<String,Feed>() {

    private var feedLiveDataSource:MutableLiveData<ItemKeyedDataSource<String, Feed>>
     = MutableLiveData()
    override fun create(): DataSource<String, Feed> {

            val feedDataSource  = FeedDataSource()
            feedLiveDataSource.postValue(feedDataSource)
            return feedDataSource
    }

    fun getFeedLiveDataSource():MutableLiveData<ItemKeyedDataSource<String,Feed>>{
        return feedLiveDataSource
    }
}