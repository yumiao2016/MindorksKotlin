package com.ym.mindorkskotlin.ui.splash.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ym.mindorkskotlin.R
import com.ym.mindorkskotlin.databinding.ActivitySplashBinding
import com.ym.mindorkskotlin.ui.base.view.BaseActivity
import com.ym.mindorkskotlin.ui.base.viewmodel.event.NavigationEvent
import com.ym.mindorkskotlin.ui.splash.viewmodel.SplashViewModel

class SplashActivity: BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribe(NavigationEvent::class.java, Observer { event -> navigateToNextPage(event) })
        viewModel.startSeeding()
    }

    private fun navigateToNextPage(event: NavigationEvent) {
        startActivity(Intent(this, event.destinationClass))
        finish()
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun createViewModel() =
        ViewModelProviders.of(this, viewModelProviderFactory).get(SplashViewModel::class.java)
}