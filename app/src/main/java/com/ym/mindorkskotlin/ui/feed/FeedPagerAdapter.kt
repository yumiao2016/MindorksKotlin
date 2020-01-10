package com.ym.mindorkskotlin.ui.feed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ym.mindorkskotlin.ui.feed.blog.view.BlogFragment
import com.ym.mindorkskotlin.ui.feed.opensource.view.OpenSourceFragment
import java.lang.IllegalArgumentException

class FeedPagerAdapter(private val fragmentManager: FragmentManager, private val mTabCount: Int)
    : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return BlogFragment.newInstance()
            1 -> return OpenSourceFragment.newInstance()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount() = mTabCount
}