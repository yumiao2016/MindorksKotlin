package com.ym.mindorkskotlin.ui.about.viewmodel

import com.ym.mindorkskotlin.ui.base.viewmodel.BaseViewModel
import com.ym.mindorkskotlin.ui.base.viewmodel.event.BackEvent

class AboutViewModel : BaseViewModel() {
    fun onNavBackClick() = publish(BackEvent())
}