package com.ym.mindorkskotlin.ui.feed.blog.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ym.mindorkskotlin.api.dto.Blog
import com.ym.mindorkskotlin.databinding.ItemBlogEmptyViewBinding
import com.ym.mindorkskotlin.databinding.ItemBlogViewBinding
import com.ym.mindorkskotlin.ui.base.view.BaseViewHolder

class BlogAdapter(private val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {
    companion object{
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE_NORMAL = 1
    }

    private var mListener: BlogAdapterListener? = null

    var blogList: List<Blog> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int) = if (blogList.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_NORMAL

    override fun getItemCount() = if (blogList.isEmpty()) 1 else blogList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when(viewType){
            VIEW_TYPE_NORMAL -> {
                val blogViewBinding = ItemBlogViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return BlogViewHolder(blogViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemBlogEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> throw IllegalArgumentException("Illegal view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    fun onItemClick(blog: Blog){
        try {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(blog.blogUrl)
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onRetryClick(){
        mListener?.onRetryClick()
    }

    inner class BlogViewHolder(private val binding: ItemBlogViewBinding) : BaseViewHolder(binding.root){
        override fun onBind(position: Int) {
            val blog = blogList[position]
            binding.adapter = this@BlogAdapter
            binding.item = blog
            binding.executePendingBindings()
        }
    }

    inner class EmptyViewHolder(private val binding: ItemBlogEmptyViewBinding) : BaseViewHolder(binding.root){
        override fun onBind(position: Int) {
            binding.adapter = this@BlogAdapter
        }
    }

    fun setListener(listener: BlogAdapterListener) {
        this.mListener = listener
    }

    interface BlogAdapterListener {
        fun onRetryClick()
    }
}