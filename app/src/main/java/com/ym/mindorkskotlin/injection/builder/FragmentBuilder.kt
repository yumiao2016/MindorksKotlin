package com.ym.mindorkskotlin.injection.builder

import com.ym.mindorkskotlin.ui.feed.blog.view.BlogFragment
import com.ym.mindorkskotlin.ui.feed.opensource.view.OpenSourceFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector()
    abstract fun provideBlogFragmentFactory(): BlogFragment

    @ContributesAndroidInjector()
    abstract fun provideOpenSourceFragmentFactory(): OpenSourceFragment
}