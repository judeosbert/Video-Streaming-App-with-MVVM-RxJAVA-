package com.butterfly.klepto.faildragon.widgets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.butterfly.klepto.faildragon.R

open class VideoRecyclerViewAdapter: RecyclerView.Adapter<VideoRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoRecyclerViewHolder {
        return VideoRecyclerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.video_content_layout,parent,false)
        )
    }

    override fun getItemCount(): Int = 0

    override fun onBindViewHolder(p0: VideoRecyclerViewHolder, p1: Int) {
        }

    override fun onViewDetachedFromWindow(holder: VideoRecyclerViewHolder) {
            holder.videoPlayer.stop()
//            holder.videoPlayer.release()
//            holder.videoPlayer.invalidate()

        super.onViewDetachedFromWindow(holder)
    }
    override fun onViewRecycled(holder: VideoRecyclerViewHolder) {
            holder.videoPlayer.stop()
//            holder.videoPlayer.release()
//            holder.videoPlayer.invalidate()

        super.onViewRecycled(holder)
    }

    override fun getItemViewType(position: Int): Int  = 0
}