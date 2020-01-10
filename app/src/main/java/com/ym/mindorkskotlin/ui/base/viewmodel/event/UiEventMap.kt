package com.ym.mindorkskotlin.ui.base.viewmodel.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class UiEventMap {
    private val events = HashMap<Class<out UiEvent>, SingleLiveEvent<out UiEvent>>()

    fun <T : UiEvent> subscribe(lifecycleOwner: LifecycleOwner, eventClass: Class<T>, eventObserver: Observer<T>){
        var liveEvent = events[eventClass]
        if(liveEvent == null){
            liveEvent = initUiEvent(eventClass)
        }
        liveEvent.observe(lifecycleOwner, Observer {
            eventObserver.onChanged(it as T)
        })
    }

    fun publish(event: UiEvent){
        var liveEvent = events[event::class.java]
        if(liveEvent == null){
            liveEvent = initUiEvent(event::class.java)
        }
        liveEvent.setValueUnsafe(event)
    }

    private fun <T : UiEvent> initUiEvent(eventClass: Class<T>) : SingleLiveEvent<T>{
        val liveEvent = SingleLiveEvent<T>()
        events[eventClass] = liveEvent
        return liveEvent
    }
}