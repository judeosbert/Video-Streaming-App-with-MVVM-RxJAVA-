package com.butterfly.klepto.faildragon.fragments

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.butterfly.klepto.faildragon.R
import com.butterfly.klepto.faildragon.adapters.VideoContentViewAdapter
import com.butterfly.klepto.faildragon.viewmodel.ContentFeedViewModel
import com.butterfly.klepto.faildragon.widgets.SpeedyLinearLayoutManager
import com.butterfly.klepto.faildragon.widgets.VideoRecyclerView
import kotlinx.android.synthetic.main.home_root_fragments_layout.*
import kotlinx.android.synthetic.main.home_root_fragments_layout.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class ForYouFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    val viewModel by viewModel<ContentFeedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_root_fragments_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        viewModel.load.observe(this, Observer {
            if(it!!)
                viewModel.getTopPosts()
            videoScrollView.setLoadVariable(viewModel.load)
        })
        viewModel.feed.observe(this, Observer{
            Log.d("JUDE","Value Updated"+it?.size)
            viewModel.resetLoadFlag()
            videoScrollView.updateData(it)


        })
//        viewModel.getTopPosts()

        
    }

    private fun setupRecyclerView() {
        videoScrollView.setAdapter(VideoContentViewAdapter(),context as Activity)
        videoScrollView.layoutManager = SpeedyLinearLayoutManager(context as Activity)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(videoScrollView)



    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {


        @JvmStatic
        fun newInstance() = ForYouFragment()
    }
}
