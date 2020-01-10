package com.ym.mindorkskotlin.ui.rate.viewmodel

import com.ym.mindorkskotlin.ui.base.viewmodel.BaseViewModel
import com.ym.mindorkskotlin.ui.rate.event.DismissEvent

class RateUsViewModel : BaseViewModel(){
    fun onLaterClick() = publish(DismissEvent())
    fun onSubmitClick() = publish(DismissEvent())
}