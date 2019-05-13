package com.butterfly.klepto.faildragon.widgets

import com.halilibo.bettervideoplayer.BetterVideoCallback
import com.halilibo.bettervideoplayer.BetterVideoPlayer
import com.halilibo.bettervideoplayer.BetterVideoProgressCallback


class VideoCallBack(private val viewHolder: VideoRecyclerViewHolder) :BetterVideoCallback,BetterVideoProgressCallback {


    override fun onVideoProgressUpdate(position: Int, duration: Int) {
        val progressPercentage:Double = (position*1f/duration*1f).toDouble()
        viewHolder.setProgress(Math.floor(progressPercentage*100).toInt())
    }

    override fun onPrepared(player: BetterVideoPlayer?) {

    }

    override fun onStarted(player: BetterVideoPlayer?) {

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