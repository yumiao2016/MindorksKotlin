package com.ym.mindorkskotlin.ui.base.viewmodel.event

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>(){
    private val mPending : AtomicBoolean = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if(mPending.compareAndSet(true, false)){
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    fun setValueUnsafe(value: Any) {
        mPending.set(true)
        super.setValue(value as T)
    }
}