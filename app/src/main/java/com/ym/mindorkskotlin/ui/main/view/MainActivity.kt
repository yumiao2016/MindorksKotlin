package com.ym.mindorkskotlin.ui.main.view

import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mindorks.placeholderview.SwipeDecor
import com.ym.mindorkskotlin.R
import com.ym.mindorkskotlin.databinding.ActivityMainBinding
import com.ym.mindorkskotlin.databinding.NavHeaderMainBinding
import com.ym.mindorkskotlin.ui.about.view.AboutFragment
import com.ym.mindorkskotlin.ui.base.extensions.getScreenHeight
import com.ym.mindorkskotlin.ui.base.extensions.getScreenWidth
import com.ym.mindorkskotlin.ui.base.view.BaseActivity
import com.ym.mindorkskotlin.ui.base.viewmodel.event.NavigationEvent
import com.ym.mindorkskotlin.ui.feed.FeedActivity
import com.ym.mindorkskotlin.ui.main.viewmodel.MainViewModel
import com.ym.mindorkskotlin.ui.main.viewmodel.entity.QuestionCard
import com.ym.mindorkskotlin.ui.main.viewmodel.entity.QuestionCardData
import com.ym.mindorkskotlin.ui.rate.view.RateUsDialog
import com.ym.mindorkskotlin.utils.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(){
    override fun getLayoutId() = R.layout.activity_main

    override fun createViewModel() = ViewModelProviders.of(this, viewModelProviderFactory).get(MainViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp(){
        setSupportActionBar(toolbar)
        val drawerToggle = ActionBarDrawerToggle(this, drawerView, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerView.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        setupNavMenu()
        viewModel.onNavMenuCreated()

        setupCardContainerView()

        subscribe(NavigationEvent::class.java, Observer {
                event -> startActivity(Intent(this, event.destinationClass)); finish() })
    }

    private fun setupNavMenu() {
        val navHeaderMainBinding = DataBindingUtil.inflate<NavHeaderMainBinding>(layoutInflater,
            R.layout.nav_header_main, navigationView, false
        )
        navigationView.addHeaderView(navHeaderMainBinding.root)
        navHeaderMainBinding.viewModel = viewModel

        navigationView.setNavigationItemSelectedListener {
            item ->
                drawerView.closeDrawer(GravityCompat.START)
                when(item.itemId){
                    R.id.navItemAbout -> {showAboutFragment(); true}
                    R.id.navItemRateUs -> { RateUsDialog.newInstance().show(supportFragmentManager); true}
                    R.id.navItemFeed -> {startActivity(Intent(this, FeedActivity::class.java)); true}
                    R.id.navItemLogout -> {viewModel.logout(); true}
                    else -> false
                }
        }
    }

    private fun setupCardContainerView(){
        val screenWidth = getScreenWidth()
        val screenHeight = getScreenHeight()

        cardsContainer.builder
            .setDisplayViewCount(3)
            .setHeightSwipeDistFactor(10f)
            .setWidthSwipeDistFactor(5f)
            .setSwipeDecor(SwipeDecor()
                .setViewWidth((0.9 * screenWidth).toInt())
                .setViewHeight((0.75 * screenHeight).toInt())
                .setPaddingTop(20)
                .setSwipeRotationAngle(10)
                .setRelativeScale(0.01f))

        cardsContainer.addItemRemoveListener { count ->
            if (count == 0){
                Handler().postDelayed({ viewModel.loadQuestionCards() }, 800)
            } else {
                viewModel.removeQuestionCard()
            }
        }

        viewModel.questionCardData.addOnListChangedCallback(QuestionDataChangeListener())
    }

    inner class QuestionDataChangeListener : ObservableList.OnListChangedCallback<ObservableArrayList<QuestionCardData>>(){
        override fun onChanged(sender: ObservableArrayList<QuestionCardData>?) {

        }

        override fun onItemRangeRemoved(
            sender: ObservableArrayList<QuestionCardData>?,
            positionStart: Int,
            itemCount: Int
        ) {
        }

        override fun onItemRangeMoved(
            sender: ObservableArrayList<QuestionCardData>?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
        }

        override fun onItemRangeInserted(
            sender: ObservableArrayList<QuestionCardData>?,
            positionStart: Int,
            itemCount: Int
        ) {
            cardsContainer.removeAllViews()
            for (question in viewModel.questionCardData) {
                val optionCount = question?.options?.size
                if (optionCount == 3) {
                    cardsContainer.addView(QuestionCard(question))
                }
            }
            ViewAnimationUtils.scaleAnimateView(cardsContainer)
        }

        override fun onItemRangeChanged(
            sender: ObservableArrayList<QuestionCardData>?,
            positionStart: Int,
            itemCount: Int
        ) {
        }

    }

    override fun onResume() {
        super.onResume()
        drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawable = item.icon
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }
        when (item.itemId) {
            R.id.action_cut -> return true
            R.id.action_copy -> return true
            R.id.action_share -> return true
            R.id.action_delete -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onFragmentDetached(tag: String){
        supportFragmentManager.findFragmentByTag(tag)?.apply {
            supportFragmentManager
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .remove(this)
                .commitNow()
            unlockDrawer()
        }
    }

    private fun lockDrawer() = drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    private fun unlockDrawer() = drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(AboutFragment.TAG)
        if (fragment == null) {
            super.onBackPressed()
        } else {
            onFragmentDetached(AboutFragment.TAG)
        }
    }

    private fun showAboutFragment() {
        lockDrawer()
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(R.id.clRootView, AboutFragment.newInstance(), AboutFragment.TAG)
            .commit()
    }
}