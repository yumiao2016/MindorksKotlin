package com.ym.mindorkskotlin.injection.component

import android.app.Application
import com.ym.mindorkskotlin.MvvmApp
import com.ym.mindorkskotlin.injection.builder.ActivityBuilder
import com.ym.mindorkskotlin.injection.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {
    fun inject(app: MvvmApp)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application) : Builder

        fun build() : AppComponent
    }
}