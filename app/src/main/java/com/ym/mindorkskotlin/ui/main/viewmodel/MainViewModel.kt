package com.ym.mindorkskotlin.ui.main.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.ym.mindorkskotlin.BuildConfig
import com.ym.mindorkskotlin.model.login.LoginDataManager
import com.ym.mindorkskotlin.model.question.QuestionDataManager
import com.ym.mindorkskotlin.ui.base.viewmodel.BaseViewModel
import com.ym.mindorkskotlin.ui.base.viewmodel.event.ErrorEvent
import com.ym.mindorkskotlin.ui.base.viewmodel.event.NavigationEvent
import com.ym.mindorkskotlin.ui.login.view.LoginActivity
import com.ym.mindorkskotlin.ui.main.viewmodel.entity.QuestionCardData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val loginDataManager: LoginDataManager, private val questionDataManager: QuestionDataManager) : BaseViewModel(){
    companion object{
        private const val TAG = "MainViewModel"
    }

    val questionCardData = ObservableArrayList<QuestionCardData>()
    val userEmail = ObservableField<String>()
    val userName = ObservableField<String>()
    val appVersion = ObservableField<String>()

    init {
        appVersion.set("version ${BuildConfig.VERSION_NAME}")
        loadQuestionCards()
    }

    fun loadQuestionCards() {
        addSubscription(questionDataManager
            .getQuestionCardData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ questionList ->
                if (questionList != null) {
                    questionCardData.clear()
                    questionCardData.addAll(questionList)
                }
            }, { throwable -> Log.e(TAG, "loadQuestionCards: $throwable") })
        )
    }

    fun removeQuestionCard() {
        questionCardData.removeAt(0)
    }

    fun logout(){
        setIsLoading(true)
        addSubscription(loginDataManager.doLogoutApiCall()
            .doOnNext { loginDataManager.setUserAsLoggedOut() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setIsLoading(false)
                publish(NavigationEvent(LoginActivity::class.java))
            }, {
                throwable ->
                setIsLoading(false)
                publish(ErrorEvent(throwable))
            }))
    }

    fun onNavMenuCreated(){
        userName.set(loginDataManager.getCurrentUserName())
        userEmail.set(loginDataManager.getCurrentUserEmail())
    }
}