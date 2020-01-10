package com.ym.mindorkskotlin.ui.feed.blog.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ym.mindorkskotlin.api.dto.Blog
import com.ym.mindorkskotlin.model.feedback.FeedBackDataManager
import com.ym.mindorkskotlin.ui.base.viewmodel.BaseViewModel
import com.ym.mindorkskotlin.ui.base.viewmodel.event.ErrorEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BlogViewModel(private val feedBackDataManager: FeedBackDataManager) : BaseViewModel() {
    val blogListLiveData = MutableLiveData<List<Blog>>()

    init {
        fetchBlogs()
    }

    fun fetchBlogs(){
        setIsLoading(true)
        addSubscription(feedBackDataManager
            .getBlogApiCall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setIsLoading(false)
                it.data?.apply { blogListLiveData.value = this }
            }, {
                setIsLoading(false)
                publish(ErrorEvent(it))
            }))
    }
}