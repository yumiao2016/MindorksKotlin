package com.ym.mindorkskotlin.injection.builder

import com.ym.mindorkskotlin.ui.feed.FeedActivity
import com.ym.mindorkskotlin.ui.login.view.LoginActivity
import com.ym.mindorkskotlin.ui.main.view.MainActivity
import com.ym.mindorkskotlin.ui.splash.view.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentBuilder::class])
    internal abstract fun bindFeedActivity(): FeedActivity
}