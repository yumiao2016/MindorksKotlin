package com.ym.mindorkskotlin.ui.feed.blog.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ym.mindorkskotlin.R
import com.ym.mindorkskotlin.databinding.FragmentBlogBinding
import com.ym.mindorkskotlin.ui.base.view.BaseFragment
import com.ym.mindorkskotlin.ui.feed.blog.viewmodel.BlogViewModel
import kotlinx.android.synthetic.main.fragment_blog.*

class BlogFragment : BaseFragment<FragmentBlogBinding, BlogViewModel>(), BlogAdapter.BlogAdapterListener {
    companion object{
        @JvmStatic fun newInstance() : BlogFragment{
            val fragment = BlogFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    private val adapter: BlogAdapter by lazy(LazyThreadSafetyMode.NONE) { BlogAdapter(context!!) }

    override fun getLayoutId() = R.layout.fragment_blog

    override fun createViewModel() = ViewModelProviders.of(this, viewModelProviderFactory).get(BlogViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        blogRecyclerView.layoutManager = LinearLayoutManager(context)
        blogRecyclerView.itemAnimator = DefaultItemAnimator()
        blogRecyclerView.adapter = adapter

        adapter.setListener(this)
        viewModel.blogListLiveData.observe(this, Observer {
            adapter.blogList = it
        })
    }

    override fun onRetryClick() = viewModel.fetchBlogs()
}