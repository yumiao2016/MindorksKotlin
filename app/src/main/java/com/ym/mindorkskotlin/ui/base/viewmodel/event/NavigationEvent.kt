package com.ym.mindorkskotlin.ui.base.viewmodel.event

import android.app.Activity

class NavigationEvent(val destinationClass: Class<out Activity>) : UiEvent() {
}