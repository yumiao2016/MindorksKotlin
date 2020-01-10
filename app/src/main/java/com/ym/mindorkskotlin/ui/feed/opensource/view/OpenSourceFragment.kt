package com.ym.mindorkskotlin.ui.feed.opensource.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ym.mindorkskotlin.R
import com.ym.mindorkskotlin.databinding.FragmentOpenSourceBinding
import com.ym.mindorkskotlin.ui.base.view.BaseFragment
import com.ym.mindorkskotlin.ui.feed.opensource.viewmodel.OpenSourceViewModel
import kotlinx.android.synthetic.main.fragment_open_source.*

class OpenSourceFragment : BaseFragment<FragmentOpenSourceBinding, OpenSourceViewModel>(), OpenSourceAdapter.OpenSourceAdapterListener {
    companion object{
        @JvmStatic fun newInstance() : OpenSourceFragment{
            val fragment = OpenSourceFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    private val adapter: OpenSourceAdapter by lazy(LazyThreadSafetyMode.NONE) { OpenSourceAdapter() }

    override fun getLayoutId() = R.layout.fragment_open_source

    override fun createViewModel() = ViewModelProviders.of(this, viewModelProviderFactory).get(OpenSourceViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openSourceRecyclerView.layoutManager = LinearLayoutManager(context)
        openSourceRecyclerView.itemAnimator = DefaultItemAnimator()
        openSourceRecyclerView.adapter = adapter

        adapter.setListener(this)
        viewModel.openSourceItemsLiveData.observe(this, Observer {
            adapter.repoList = it
        })
    }

    override fun onRetryClick() = viewModel.fetchRepos()
}