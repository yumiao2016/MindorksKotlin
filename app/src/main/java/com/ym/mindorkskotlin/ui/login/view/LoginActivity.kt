package com.ym.mindorkskotlin.ui.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ym.mindorkskotlin.R
import com.ym.mindorkskotlin.databinding.ActivityLoginBinding
import com.ym.mindorkskotlin.ui.base.view.BaseActivity
import com.ym.mindorkskotlin.ui.base.viewmodel.event.ErrorEvent
import com.ym.mindorkskotlin.ui.base.viewmodel.event.NavigationEvent
import com.ym.mindorkskotlin.ui.login.viewmodel.LoginViewModel

class LoginActivity: BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun getLayoutId() = R.layout.activity_login

    override fun createViewModel() = ViewModelProviders.of(this, viewModelProviderFactory).get(LoginViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribe(ErrorEvent::class.java, Observer { event -> Toast.makeText(this, event.throwable.message, Toast.LENGTH_SHORT).show() })
        subscribe(NavigationEvent::class.java, Observer { event -> startActivity(Intent(this, event.destinationClass)); finish()})
    }
}