package com.ym.mindorkskotlin.ui.rate.view

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ym.mindorkskotlin.R
import com.ym.mindorkskotlin.databinding.DialogRateUsBinding
import com.ym.mindorkskotlin.ui.base.view.BaseDialog
import com.ym.mindorkskotlin.ui.rate.event.DismissEvent
import com.ym.mindorkskotlin.ui.rate.viewmodel.RateUsViewModel

class RateUsDialog : BaseDialog<DialogRateUsBinding, RateUsViewModel>() {
    companion object {
        private val TAG = RateUsDialog::class.java.simpleName

        @JvmStatic fun newInstance(): RateUsDialog {
            val fragment = RateUsDialog()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.dialog_rate_us

    override fun createViewModel() = ViewModelProviders.of(this).get(RateUsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribe(DismissEvent::class.java, Observer { dismissDialog(TAG) })
    }

    fun show(fragmentManager: FragmentManager) = show(fragmentManager, TAG)
}