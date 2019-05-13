package com.butterfly.klepto.faildragon.widgets

import android.net.Uri
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.butterfly.klepto.faildragon.R
import com.butterfly.klepto.faildragon.modal.Feed
import com.butterfly.klepto.faildragon.utils.getCacheFile
import com.butterfly.klepto.faildragon.utils.isFileInCache
import com.halilibo.bettervideoplayer.BetterVideoPlayer

open class VideoRecyclerViewHolder constructor(private val view: View): RecyclerView.ViewHolder(view) {
    val videoPlayer: BetterVideoPlayer = view.findViewById(R.id.contentVideoView)
    private val postOwner  = view.findViewById(R.id.postAuthorName) as TextView
    private val postDescription = view.findViewById(R.id.postDescription) as TextView
    private val progressBar = view.findViewById(R.id.progressBar) as ProgressBar
    private val playButton = view.findViewById(R.id.btnPlay) as ImageView
    private val mCallBack: VideoCallBack = VideoCallBack(this)
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
        videoPlayer.setCallback(mCallBack)
        videoPlayer.setProgressCallback(mCallBack)
        postOwner.text = "@${feed.postOwner}"
        postDescription.text = feed.postTitle
        if(isFileInCache(feed.videoUrl?:"")){
            videoPlayer.setSource(
                Uri.fromFile(getCacheFile(feed.videoUrl?:""))
            )
        }else{
            videoPlayer.setSource(Uri.parse(feed.videoUrl))
        }

        videoPlayer.setAutoPlay(true)
        playButton.visibility = View.GONE

}


    fun pauseVideo() {
        playButton.visibility = View.VISIBLE
        videoPlayer.pause()
    }

    fun startVideo() {
        playButton.visibility = View.GONE
        videoPlayer.start()
    }

        fun setProgress(progress:Int){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressBar.setProgress(progress,true)
            }else{
                progressBar.progress = progress
            }
        }
    }