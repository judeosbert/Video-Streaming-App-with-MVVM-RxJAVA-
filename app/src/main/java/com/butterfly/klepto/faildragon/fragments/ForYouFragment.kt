package com.butterfly.klepto.faildragon.fragments

import android.arch.lifecycle.Observer
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.butterfly.klepto.faildragon.R
import com.butterfly.klepto.faildragon.viewmodel.ContentFeedViewModel
import com.butterfly.klepto.faildragon.widgets.VideoRecyclerViewAdapter
import com.butterfly.klepto.faildragon.widgets.VideoRecyclerViewHolder
import kotlinx.android.synthetic.main.home_root_fragments_layout.*
import org.koin.android.viewmodel.ext.android.viewModel


class ForYouFragment : Fragment() {

    private val viewModel by viewModel<ContentFeedViewModel>()
    private val feedScrollViewAdapter = VideoRecyclerViewAdapter()
    var isVideoPaused:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_root_fragments_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressCircular.progressTintList = ColorStateList.valueOf(Color.RED)
        setupRecyclerView()
        viewModel.feedPagedList.observe(this, Observer{
            Log.d("JUDE","Value Updated"+it?.size)
            feedScrollViewAdapter.submitList(it)
            feedScrollViewAdapter.notifyDataSetChanged()
            progressCircular.visibility = View.GONE
        })
    }

    override fun onStop() {
        pauseVideo()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        startVideo()
    }


    private fun pauseVideo(){
        isVideoPaused = true
        val layoutManager = videoScrollView.layoutManager as LinearLayoutManager
        val currentVideoPos = layoutManager.findFirstCompletelyVisibleItemPosition()
        val viewHolder = videoScrollView.findViewHolderForAdapterPosition(currentVideoPos) as VideoRecyclerViewHolder
        viewHolder.pauseVideo()

    }
    private fun startVideo(){
        if(isVideoPaused) {
            isVideoPaused = false
            val layoutManager = videoScrollView.layoutManager as LinearLayoutManager
            val currentVideoPos = layoutManager.findFirstCompletelyVisibleItemPosition()
            val viewHolder = videoScrollView.findViewHolderForAdapterPosition(currentVideoPos) as VideoRecyclerViewHolder
            viewHolder.startVideo()
        }

    }

    private fun setupRecyclerView() {
        videoScrollView.adapter = feedScrollViewAdapter
        videoScrollView.layoutManager = LinearLayoutManager(context)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(videoScrollView)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForYouFragment()
    }
}
