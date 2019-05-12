package com.butterfly.klepto.faildragon.widgets

import android.content.Context
import com.butterfly.klepto.faildragon.modal.Feed
import com.butterfly.klepto.faildragon.utils.FileCacher
import com.butterfly.klepto.faildragon.utils.isFileInCache
import com.halilibo.bettervideoplayer.BetterVideoCallback
import com.halilibo.bettervideoplayer.BetterVideoPlayer
import com.halilibo.bettervideoplayer.BetterVideoProgressCallback
import org.koin.core.KoinComponent
import org.koin.core.inject


class VideoCallBack(
    private val viewHolder: VideoRecyclerViewHolder,
    val mData: ArrayList<Feed>,
    val currentPosition: Int,
    val mContext:Context) :BetterVideoCallback,BetterVideoProgressCallback,KoinComponent {

    val fileCacher by inject<FileCacher>()
    override fun onVideoProgressUpdate(position: Int, duration: Int) {
        val progressPercentage:Double = (position*1f/duration*1f).toDouble()
//            Log.d("JUDE","Percentage"+progressPercentage*100)

        viewHolder.setProgress(Math.floor(progressPercentage*100).toInt())
    }

    override fun onPrepared(player: BetterVideoPlayer?) {

    }

    override fun onStarted(player: BetterVideoPlayer?) {
        try {
            fileCacher.cacheFile(mContext, mData[currentPosition].videoUrl ?: "")
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }
    }

    override fun onBuffering(percent: Int) {



    }

    override fun onPreparing(player: BetterVideoPlayer?) {
    }

    override fun onError(player: BetterVideoPlayer?, e: Exception?) {
    }

    override fun onToggleControls(player: BetterVideoPlayer?, isShowing: Boolean) {
    }

    override fun onPaused(player: BetterVideoPlayer?) {
    }

    override fun onCompletion(player: BetterVideoPlayer?) {
        player?.start()
        viewHolder.setProgress(100)
    }
}