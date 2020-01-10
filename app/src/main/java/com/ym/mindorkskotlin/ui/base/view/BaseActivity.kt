package com.ym.mindorkskotlin.ui.base.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.ym.mindorkskotlin.BR
import com.ym.mindorkskotlin.ui.base.viewmodel.BaseViewModel
import com.ym.mindorkskotlin.ui.base.viewmodel.ViewModelProviderFactory
import com.ym.mindorkskotlin.ui.base.viewmodel.event.UiEvent
import dagger.android.AndroidInjection
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel> : AppCompatActivity(){
    @Inject
    protected lateinit var viewModelProviderFactory: ViewModelProviderFactory

    protected val viewModel by lazy(LazyThreadSafetyMode.NONE) { createViewModel() }
    protected lateinit var binding: T

    abstract fun getLayoutId(): Int

    abstract fun createViewModel(): V

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    protected fun <T: UiEvent> subscribe(eventClass: Class<T>, eventObserver: Observer<T>) =
        viewModel.subscribe(this, eventClass, eventObserver)

    open fun onFragmentAttached() {

    }

    open fun onFragmentDetached(tag: String) {

    }
}