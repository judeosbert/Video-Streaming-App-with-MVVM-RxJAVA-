package com.butterfly.klepto.faildragon.widgets

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.butterfly.klepto.faildragon.modal.Feed
import com.butterfly.klepto.faildragon.utils.FileCacher
import com.butterfly.klepto.faildragon.utils.isFileInCache
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.collections.ArrayList

class VideoRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null , defStyle: Int = 0):RecyclerView(context, attrs, defStyle),KoinComponent {
    private var playOnlyFirstVideo = false
    private var downloadVideos = false
    private var checkForMp4 = true
    private var visiblePercent = 100.0f
    private lateinit var activity:Activity
    private var mData = arrayListOf<Feed>()
    private var load: MutableLiveData<Boolean> = MutableLiveData()
    private val mCacher by inject<FileCacher>()
    init {
        this.recycledViewPool.setMaxRecycledViews(0,0)
    }

     fun setAdapter(adapter: Adapter<*>?,activity: Activity) {
        super.setAdapter(adapter)
        addCustomScrollListener()
         this.activity = activity


    }

    private fun addCustomScrollListener(){
        this.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if((((recyclerView.layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition() == mData.size-3))
                    || mData.size<=3
                    && !load.value!!){
                    load.value = true
                }
                playAvailableVideos(newState)
            }


        })
    }

    private fun playAvailableVideos(newState:Int){
        val handlerThread =
            HandlerThread("DONT_GIVE_UP", Process.THREAD_PRIORITY_BACKGROUND + Process.THREAD_PRIORITY_MORE_FAVORABLE)
        handlerThread.start()
        val looper = handlerThread.looper
        val handler = Handler(looper)
        val runnables = ArrayList<Runnable>()

//        List<HandlerThread> threads = new ArrayList<HandlerThread>();
        if (newState == 0) {
            val firstVisiblePosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val lastVisiblePosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            //            Log.d("trace", "firstVisiblePosition: " + firstVisiblePosition + " |lastVisiblePosition: " + lastVisiblePosition);
            if (firstVisiblePosition >= 0 && lastVisiblePosition < mData.size) {
                val rect_parent = Rect()
                getGlobalVisibleRect(rect_parent)
                //                        Log.d("pos", "recyclerview left: " + rect_parent.left + " | right: " + rect_parent.right + " | top: " + rect_parent.top + " | bottom: " + rect_parent.bottom);
                    var foundFirstVideo = false
                    for (i in firstVisiblePosition..lastVisiblePosition) {
                        val holder = findViewHolderForAdapterPosition(i)
                        try {
                            val cvh = holder as VideoRecyclerViewHolder
                            if (i >= 0) {
                                val location = IntArray(2)
                                val callBack = VideoCallBack(cvh,mData,i,this.context)
                                cvh.videoPlayer.getLocationOnScreen(location)
                                cvh.videoPlayer.setProgressCallback(callBack)
                                cvh.videoPlayer.setCallback(callBack)
                                val rect_child = Rect(
                                    location[0],
                                    location[1],
                                    location[0] + cvh.videoPlayer.width,
                                    location[1] + cvh.videoPlayer.height
                                )
                                //                                        Log.d("k9pos", "x: " + location[0] + " | x right: " + (location[0] + cvh.getAah_vi().getWidth()) + " | y: " + location[1] + " | y bottom: " + (location[1] + cvh.getAah_vi().getHeight()));
                                //                                Log.d("trace", i + " contains: " + rect_parent.contains(rect_child));
                                val rect_parent_area =
                                    ((rect_child.right - rect_child.left) * (rect_child.bottom - rect_child.top)).toFloat()
                                val x_overlap = Math.max(
                                    0,
                                    Math.min(rect_child.right, rect_parent.right) - Math.max(
                                        rect_child.left,
                                        rect_parent.left
                                    )
                                ).toFloat()
                                val y_overlap = Math.max(
                                    0,
                                    Math.min(rect_child.bottom, rect_parent.bottom) - Math.max(
                                        rect_child.top,
                                        rect_parent.top
                                    )
                                ).toFloat()
                                val overlapArea = x_overlap * y_overlap
                                val percent = overlapArea / rect_parent_area * 100.0f
                                if (!foundFirstVideo && percent >= visiblePercent) {
                                    //                                    Log.d("trace", i + " foundFirstVideo: " + cvh.getVideoUrl());
                                    foundFirstVideo = true
                                    mData[i].videoCached = isFileInCache(context, mData[i].videoUrl?:"")
                                    activity.runOnUiThread {
                                        holder.playButton.visibility = View.GONE
                                        (holder).initVideoView(mData.get(i))
                                        //
                                    }
                                } else {
                                    //
                                    //                                    Log.d("trace", i + " not foundFirstVideo: ");

                                    activity.runOnUiThread {
                                        holder.pauseVideo()
                                //
                                    }
                                }
                            }
                        } catch (e: Exception) {

                            e.printStackTrace()
                        }

                    }

            }
        } else if (runnables.size > 0) {
            for (t in runnables) {
                handler.removeCallbacksAndMessages(t)
            }
            runnables.clear()
            handlerThread.quit()
        }



    }

    fun startFirstVideo() {
        this.smoothScrollBy(0,1)
        this.smoothScrollBy(0,-1)
    }

    fun updateData(it: Collection<Feed>?) {
        try {
            mData.addAll(it?.asIterable()!!)
//            if(mData.size == it.size){
                startFirstVideo()
            val currentPosition = (this.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition();
            try {
                    mCacher.cacheFile(context, mData,currentPosition)

//            }
            }catch (e:java.lang.Exception){
                e.printStackTrace()
            }
        }catch (e:NullPointerException){
            e.printStackTrace()
        }
    }

    fun setLoadVariable(load: MutableLiveData<Boolean>) {
            this.load = load
    }
}