package com.ym.mindorkskotlin.ui.base.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ym.mindorkskotlin.model.feedback.FeedBackDataManager
import com.ym.mindorkskotlin.model.login.LoginDataManager
import com.ym.mindorkskotlin.model.question.QuestionDataManager
import com.ym.mindorkskotlin.ui.feed.blog.viewmodel.BlogViewModel
import com.ym.mindorkskotlin.ui.feed.opensource.viewmodel.OpenSourceViewModel
import com.ym.mindorkskotlin.ui.login.viewmodel.LoginViewModel
import com.ym.mindorkskotlin.ui.main.viewmodel.MainViewModel
import com.ym.mindorkskotlin.ui.splash.viewmodel.SplashViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelProviderFactory @Inject constructor(private val context: Context,
                                                   private val loginDataManager: LoginDataManager,
                                                   private val questionDataManager: QuestionDataManager,
                                                   private val feedBackDataManager: FeedBackDataManager) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> return SplashViewModel(questionDataManager, loginDataManager) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> return LoginViewModel(context, loginDataManager) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) -> return MainViewModel(loginDataManager, questionDataManager) as T
            modelClass.isAssignableFrom(BlogViewModel::class.java) -> return BlogViewModel(feedBackDataManager) as T
            modelClass.isAssignableFrom(OpenSourceViewModel::class.java) -> return OpenSourceViewModel(feedBackDataManager) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}");
        }
    }
}