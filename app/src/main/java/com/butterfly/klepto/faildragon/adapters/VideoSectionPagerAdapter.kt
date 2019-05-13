package com.butterfly.klepto.faildragon.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.butterfly.klepto.faildragon.fragments.ForYouFragment

class VideoSectionPagerAdapter(val context: Context, val fragmentManager:FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return ForYouFragment.newInstance()

        }



    override fun getCount(): Int {
            return 1
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "For You"
        }
    }
