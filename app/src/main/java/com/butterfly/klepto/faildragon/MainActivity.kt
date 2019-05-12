package com.butterfly.klepto.faildragon

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TabLayout
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.butterfly.klepto.faildragon.adapters.VideoSectionPagerAdapter
import com.butterfly.klepto.faildragon.extensions.sizeScaleAnimation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_tab_item_layout.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
    }

    fun initializeViews(){

        videoListTabLayout.addTab(videoListTabLayout.newTab().setText("Following"))
        videoListTabLayout.addTab(videoListTabLayout.newTab().setText("For You"))
        val videoSectionPagerAdapter = VideoSectionPagerAdapter(this,supportFragmentManager)
        contentViewPager.adapter = videoSectionPagerAdapter
        videoListTabLayout.setupWithViewPager(contentViewPager)
        contentViewPager.currentItem = 1
        setCustomTabItemLayout()

    }
    fun setCustomTabItemLayout(){
        for(position in 0..videoListTabLayout.tabCount){
            val tab:TabLayout.Tab?  = videoListTabLayout.getTabAt(position)
            var customLayout  = LayoutInflater.from(this)
                .inflate(R.layout.custom_tab_item_layout,videoListTabLayout,false)
                as ConstraintLayout
            customLayout.tabTitle.text = tab?.text
            if(position == 1)
                customLayout.tabDivider.visibility = View.GONE
            tab?.customView = customLayout
            tab?.select()
        }

    }
}
