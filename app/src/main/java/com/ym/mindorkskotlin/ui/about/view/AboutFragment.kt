package com.ym.mindorkskotlin.ui.about.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ym.mindorkskotlin.R
import com.ym.mindorkskotlin.databinding.FragmentAboutBinding
import com.ym.mindorkskotlin.ui.about.viewmodel.AboutViewModel
import com.ym.mindorkskotlin.ui.base.view.BaseFragment

class AboutFragment : BaseFragment<FragmentAboutBinding, AboutViewModel>(){
    companion object{
        @JvmField val TAG = AboutFragment::class.java.simpleName
        @JvmStatic fun newInstance() : AboutFragment{
            val fragment = AboutFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_about

    override fun createViewModel() = ViewModelProviders.of(this).get(AboutViewModel::class.java)
}