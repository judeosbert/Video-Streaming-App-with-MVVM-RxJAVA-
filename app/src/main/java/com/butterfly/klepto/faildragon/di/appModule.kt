package com.butterfly.klepto.faildragon.di

import com.butterfly.klepto.faildragon.apimanagers.FileCacheApiManager
import com.butterfly.klepto.faildragon.apimanagers.RedditApiManager
import com.butterfly.klepto.faildragon.apimanagers.TwitchApiManager
import com.butterfly.klepto.faildragon.utils.FileCacher
import com.butterfly.klepto.faildragon.viewmodel.ContentFeedViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule:Module = module {
    viewModel { ContentFeedViewModel() }
    single { RedditApiManager() }
    single { TwitchApiManager() }
    single { FileCacheApiManager() }
    single { FileCacher() }

}