package com.butterfly.klepto.faildragon.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.butterfly.klepto.faildragon.modal.Feed
import com.butterfly.klepto.faildragon.repository.ContentFeedRespository

class ContentFeedViewModel (private val repository: ContentFeedRespository):ViewModel(){

    var feed: MediatorLiveData<Collection<Feed>> = MediatorLiveData()
    var repositoryFetchFunction = repository.getTopPosts()
    fun getTopPosts(){
        repository.getTopPosts()
    }
    var load:MutableLiveData<Boolean> = MutableLiveData()
    init {
        //load.value = true
        feed.addSource(repositoryFetchFunction){
//
            feed.value = it

        }
    }

    fun resetLoadFlag(){
        load.value = false
    }

}