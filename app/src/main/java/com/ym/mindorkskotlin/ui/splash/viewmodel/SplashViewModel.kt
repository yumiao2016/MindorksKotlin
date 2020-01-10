package com.ym.mindorkskotlin.ui.splash.viewmodel

import com.ym.mindorkskotlin.model.login.LoginDataManager
import com.ym.mindorkskotlin.model.login.entity.LoggedInMode
import com.ym.mindorkskotlin.model.question.QuestionDataManager
import com.ym.mindorkskotlin.ui.base.viewmodel.BaseViewModel
import com.ym.mindorkskotlin.ui.base.viewmodel.event.NavigationEvent
import com.ym.mindorkskotlin.ui.login.view.LoginActivity
import com.ym.mindorkskotlin.ui.main.view.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashViewModel(private val questionDataManager: QuestionDataManager,
                      private var loginDataManager: LoginDataManager) : BaseViewModel() {
    fun startSeeding() =
            addSubscription(questionDataManager
                .seedDatabaseQuestions()
                .flatMap { questionDataManager.seedDatabaseOptions() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({decideNextActivity()}, {decideNextActivity()}))

    private fun decideNextActivity() {
        if(LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type == loginDataManager.getCurrentUserLoggedInMode()){
            publish(NavigationEvent(LoginActivity::class.java))
        } else {
            publish(NavigationEvent(MainActivity::class.java))
        }
    }
}