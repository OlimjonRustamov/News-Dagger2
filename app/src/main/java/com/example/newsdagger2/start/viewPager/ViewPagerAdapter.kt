package com.example.newsdagger2.start.viewPager

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, var imageList:ArrayList<Int>) : FragmentStatePagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT

) {
    override fun getCount(): Int = imageList.size


    override fun getItem(position: Int): Fragment {
        return ViewPagerItemFragment.newInstance(imageList[position])
    }
}