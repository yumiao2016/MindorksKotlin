package com.ym.mindorkskotlin.ui.base.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.ym.mindorkskotlin.BR
import com.ym.mindorkskotlin.ui.base.viewmodel.BaseViewModel
import com.ym.mindorkskotlin.ui.base.viewmodel.ViewModelProviderFactory
import com.ym.mindorkskotlin.ui.base.viewmodel.event.BackEvent
import com.ym.mindorkskotlin.ui.base.viewmodel.event.UiEvent
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<T: ViewDataBinding, V: BaseViewModel> : DialogFragment() {
    @Inject
    protected lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var mActivity: BaseActivity<*, *>? = null
    protected val viewModel by lazy(LazyThreadSafetyMode.NONE) { createViewModel() }
    protected lateinit var binding: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>){
            mActivity = context
            mActivity?.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)

        subscribe(BackEvent::class.java, Observer { mActivity?.onFragmentDetached(tag ?: "") })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    abstract fun getLayoutId(): Int

    abstract fun createViewModel(): V

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    protected fun <T: UiEvent> subscribe(eventClass: Class<T>, eventObserver: Observer<T>) =
        viewModel.subscribe(this, eventClass, eventObserver)
}