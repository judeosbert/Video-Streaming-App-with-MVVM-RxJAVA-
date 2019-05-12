package com.butterfly.klepto.faildragon.widgets

import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.butterfly.klepto.faildragon.R
import com.butterfly.klepto.faildragon.extensions.path
import com.butterfly.klepto.faildragon.modal.Feed
import com.butterfly.klepto.faildragon.utils.getCacheFile
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.ChasingDots
import com.halilibo.bettervideoplayer.BetterVideoPlayer

open class VideoRecyclerViewHolder constructor(val view: View): RecyclerView.ViewHolder(view) {
    val videoPlayer: BetterVideoPlayer = view.findViewById(R.id.contentVideoView)
    val postOwner  = view.findViewById(R.id.postAuthorName) as TextView
    val postDescription = view.findViewById(R.id.postDescription) as TextView
    val progressBar = view.findViewById(R.id.progressBar) as ProgressBar
    val playButton = view.findViewById(R.id.btnPlay) as ImageView
    lateinit var mFeed: Feed
    fun playVideo() = videoPlayer.start()
    init {
        this.setIsRecyclable(false)
        videoPlayer.disableControls()
        videoPlayer.setOnClickListener {
            if(videoPlayer.isPlaying)
               pauseVideo()
            else
                startVideo()
        }

    }

    fun initVideoView(feed: Feed) {
        mFeed = feed
        postOwner.text = "@${feed.postOwner}"
        postDescription.text = feed.postTitle
        if(feed.videoCached){
            videoPlayer.setSource(
                Uri.fromFile(getCacheFile(view.context,feed.videoUrl?:""))
            )
        }else{
            videoPlayer.setSource(Uri.parse(feed.videoUrl))
        }

        videoPlayer.setAutoPlay(true)

}


    fun pauseVideo() {
        playButton.visibility = View.VISIBLE
        videoPlayer.pause()
    }

    fun startVideo() {
        playButton.visibility = View.GONE
        videoPlayer.start()
    }

        fun isPlaying():Boolean{
            return videoPlayer.isPlaying
        }

        fun setProgress(progress:Int){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressBar.setProgress(progress,true)
            }else{
                progressBar.progress = progress
            }
        }
    }