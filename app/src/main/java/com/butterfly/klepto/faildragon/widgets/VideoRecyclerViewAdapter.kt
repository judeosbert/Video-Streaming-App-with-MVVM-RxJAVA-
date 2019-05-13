package com.butterfly.klepto.faildragon.widgets

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.butterfly.klepto.faildragon.R
import com.butterfly.klepto.faildragon.modal.Feed
import com.butterfly.klepto.faildragon.utils.DiffUtilCallBack

open class VideoRecyclerViewAdapter: PagedListAdapter<Feed,VideoRecyclerViewHolder>(DiffUtilCallBack()) {
    lateinit var  mContext:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoRecyclerViewHolder {
        mContext = parent.context
        return VideoRecyclerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.video_content_layout,parent,false)
        )
    }

    override fun getItemCount(): Int = currentList?.size?:0

    override fun onBindViewHolder(holder: VideoRecyclerViewHolder, position: Int) {
        holder.initVideoView(getItem(position)!!)
    }

    override fun onViewDetachedFromWindow(holder: VideoRecyclerViewHolder) {
        holder.videoPlayer.pause()
        super.onViewDetachedFromWindow(holder)
    }
    override fun onViewRecycled(holder: VideoRecyclerViewHolder) {
            holder.videoPlayer.release()

        super.onViewRecycled(holder)
    }



    override fun getItemViewType(position: Int): Int  = 0
}