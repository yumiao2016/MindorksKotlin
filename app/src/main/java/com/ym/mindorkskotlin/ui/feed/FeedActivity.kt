package com.ym.mindorkskotlin.ui.feed

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.NavUtils
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.ym.mindorkskotlin.R
import com.ym.mindorkskotlin.databinding.ActivityFeedBinding
import com.ym.mindorkskotlin.ui.base.view.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_feed.*
import javax.inject.Inject

class FeedActivity : BaseActivity<ActivityFeedBinding, FeedViewModel>(), HasAndroidInjector{
    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun getLayoutId() = R.layout.activity_feed

    override fun createViewModel() = ViewModelProviders.of(this).get(FeedViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.blog)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.open_source)))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                feedViewPager.currentItem = tab.position
            }
        })

        val adapter = FeedPagerAdapter(supportFragmentManager, 2)
        feedViewPager.adapter = adapter
        feedViewPager.offscreenPageLimit = 2
        feedViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            val upIntent = NavUtils.getParentActivityIntent(this)
            upIntent?.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                if(NavUtils.shouldUpRecreateTask(this@FeedActivity, this)){
                    TaskStackBuilder.create(this@FeedActivity)
                        .addNextIntentWithParentStack(this)
                        .startActivities()
                } else {
                    NavUtils.navigateUpTo(this@FeedActivity, this)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}