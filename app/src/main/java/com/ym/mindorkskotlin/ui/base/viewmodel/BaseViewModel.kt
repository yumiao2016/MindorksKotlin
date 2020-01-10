package com.ym.mindorkskotlin.ui.base.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ym.mindorkskotlin.ui.base.viewmodel.event.UiEvent
import com.ym.mindorkskotlin.ui.base.viewmodel.event.UiEventMap
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel(){
    private val uiEventMap = UiEventMap()
    private val compositeDisposable = CompositeDisposable()
    private val mIsLoading = ObservableBoolean()

    fun <T : UiEvent> subscribe(lifecycleOwner: LifecycleOwner, eventClass: Class<T>, eventObserver: Observer<T>) =
            uiEventMap.subscribe(lifecycleOwner, eventClass, eventObserver)

    protected fun <T : UiEvent> publish(event: T) = uiEventMap.publish(event)

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun removeSubscription(d: Disposable) {
        compositeDisposable.remove(d)
        if (!d.isDisposed) {
            d.dispose()
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }
}