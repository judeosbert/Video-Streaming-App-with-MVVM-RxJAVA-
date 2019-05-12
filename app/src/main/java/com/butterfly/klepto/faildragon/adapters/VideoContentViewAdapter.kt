package com.butterfly.klepto.faildragon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.butterfly.klepto.faildragon.R
import com.butterfly.klepto.faildragon.widgets.VideoRecyclerViewAdapter
import com.butterfly.klepto.faildragon.widgets.VideoRecyclerViewHolder
import kotlin.random.Random


class VideoContentViewAdapter: VideoRecyclerViewAdapter() {

    val list = arrayListOf<Int>(0,1,0,10,1,0,10,1,0,10,1,0,10,1,0,1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoRecyclerViewHolder {
        return VideoContentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.video_content_layout,parent,false)
        )

    }

    override fun getItemCount(): Int  = list.size


    inner class VideoContentViewHolder(view: View):VideoRecyclerViewHolder(view){

    }
}

