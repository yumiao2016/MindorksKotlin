package com.ym.mindorkskotlin.ui.feed.opensource.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ym.mindorkskotlin.api.dto.Repo
import com.ym.mindorkskotlin.model.feedback.FeedBackDataManager
import com.ym.mindorkskotlin.ui.base.viewmodel.BaseViewModel
import com.ym.mindorkskotlin.ui.base.viewmodel.event.ErrorEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by amitshekhar on 10/07/17.
 */

class OpenSourceViewModel(private val feedBackDataManager: FeedBackDataManager) : BaseViewModel() {
    val openSourceItemsLiveData = MutableLiveData<List<Repo>>()

    init {
        fetchRepos()
    }

    fun fetchRepos() {
        setIsLoading(true)
        addSubscription(feedBackDataManager
            .getOpenSourceApiCall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ openSourceResponse ->
                setIsLoading(false)
                openSourceItemsLiveData.setValue(openSourceResponse.data)
            }, { throwable ->
                setIsLoading(false)
                publish(ErrorEvent(throwable))
            }))
    }
}
