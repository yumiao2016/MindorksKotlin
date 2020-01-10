package com.ym.mindorkskotlin

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

import com.ym.mindorkskotlin.injection.component.DaggerAppComponent
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class MvvmApp : Application(), HasAndroidInjector{
    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build())
    }
}