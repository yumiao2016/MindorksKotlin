package com.ym.mindorkskotlin.ui.base.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.ym.mindorkskotlin.BR
import com.ym.mindorkskotlin.ui.base.viewmodel.BaseViewModel
import com.ym.mindorkskotlin.ui.base.viewmodel.event.UiEvent

abstract class BaseDialog<T: ViewDataBinding, V: BaseViewModel> : DialogFragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    abstract fun getLayoutId(): Int

    abstract fun createViewModel(): V

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(context)
        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val transaction = manager.beginTransaction()
        val prevFragment = manager.findFragmentByTag(tag)
        if(prevFragment != null){
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    fun dismissDialog(tag: String){
        dismiss()
        mActivity?.onFragmentDetached(tag)
    }

    protected fun <T: UiEvent> subscribe(eventClass: Class<T>, eventObserver: Observer<T>) =
        viewModel.subscribe(this, eventClass, eventObserver)
}