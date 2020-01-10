package com.ym.mindorkskotlin.ui.login.viewmodel

import android.content.Context
import android.text.TextUtils
import androidx.databinding.ObservableField
import com.ym.mindorkskotlin.R
import com.ym.mindorkskotlin.api.dto.FacebookLoginRequest
import com.ym.mindorkskotlin.api.dto.GoogleLoginRequest
import com.ym.mindorkskotlin.api.dto.ServerLoginRequest
import com.ym.mindorkskotlin.model.login.LoginDataManager
import com.ym.mindorkskotlin.model.login.entity.LoggedInMode
import com.ym.mindorkskotlin.ui.base.viewmodel.BaseViewModel
import com.ym.mindorkskotlin.ui.base.viewmodel.event.ErrorEvent
import com.ym.mindorkskotlin.ui.base.viewmodel.event.NavigationEvent
import com.ym.mindorkskotlin.ui.main.view.MainActivity
import com.ym.mindorkskotlin.utils.CommonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val context: Context,
                     private val loginDataManager: LoginDataManager): BaseViewModel() {
    val email: ObservableField<String> = ObservableField()
    val password: ObservableField<String> = ObservableField()

    fun onServerLoginClick() {
        if(isEmailAndPasswordValid(email.get(), password.get())){
            login(email.get()!!, password.get()!!)
        } else {
            publish(ErrorEvent(IllegalArgumentException(context.getString(R.string.invalid_email_password))))
        }
    }

    private fun isEmailAndPasswordValid(email: String?, password: String?) = CommonUtils.isEmailValid(email) && !TextUtils.isEmpty(password)

    private fun login(email: String, password: String){
        setIsLoading(true)
        addSubscription(loginDataManager
            .doServerLoginApiCall(ServerLoginRequest(email, password))
            .doOnNext{ response -> loginDataManager.updateUserInfo(response, LoggedInMode.LOGGED_IN_MODE_SERVER) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setIsLoading(false)
            }, {
                throwable ->
                setIsLoading(false)
                publish(ErrorEvent(throwable))
            }))
    }

    fun onFbLoginClick() {
        setIsLoading(true)
        addSubscription(loginDataManager
            .doFacebookLoginApiCall(FacebookLoginRequest("test3", "test4"))
            .doOnNext{ response -> loginDataManager.updateUserInfo(response, LoggedInMode.LOGGED_IN_MODE_FB) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setIsLoading(false)
                //getNavigator().openMainActivity()
            }, { throwable ->
                setIsLoading(false)
                publish(ErrorEvent(throwable))
            })
        )
    }

    fun onGoogleLoginClick() {
        setIsLoading(true)
        addSubscription(loginDataManager
            .doGoogleLoginApiCall(GoogleLoginRequest("test1", "test1"))
            .doOnNext{ response -> loginDataManager.updateUserInfo(response, LoggedInMode.LOGGED_IN_MODE_GOOGLE) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setIsLoading(false)
                publish(NavigationEvent(MainActivity::class.java))
            }, { throwable ->
                setIsLoading(false)
                publish(ErrorEvent(throwable))
            })
        )
    }
}