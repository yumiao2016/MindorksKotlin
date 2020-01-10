package com.ym.mindorkskotlin.ui.feed.opensource.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ym.mindorkskotlin.api.dto.Repo
import com.ym.mindorkskotlin.databinding.ItemOpenSourceEmptyViewBinding
import com.ym.mindorkskotlin.databinding.ItemOpenSourceViewBinding
import com.ym.mindorkskotlin.ui.base.view.BaseViewHolder

class OpenSourceAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    companion object{
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE_NORMAL = 1
    }

    private var mListener: OpenSourceAdapterListener? = null

    var repoList: List<Repo> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int) = if (repoList.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_NORMAL

    override fun getItemCount() = if (repoList.isEmpty()) 1 else repoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when(viewType){
            VIEW_TYPE_NORMAL -> {
                val repoViewBinding = ItemOpenSourceViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return RepoViewHolder(repoViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemOpenSourceEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> throw IllegalArgumentException("Illegal view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    inner class RepoViewHolder(private val binding: ItemOpenSourceViewBinding) : BaseViewHolder(binding.root){
        override fun onBind(position: Int) {
            val repo = repoList[position]
            binding.item = repo
            binding.executePendingBindings()
        }
    }

    inner class EmptyViewHolder(private val binding: ItemOpenSourceEmptyViewBinding) : BaseViewHolder(binding.root){
        override fun onBind(position: Int) {
            binding.adapter = this@OpenSourceAdapter
        }
    }

    fun onRetryClick(){
        mListener?.onRetryClick()
    }

    fun setListener(listener: OpenSourceAdapterListener) {
        this.mListener = listener
    }

    interface OpenSourceAdapterListener {
        fun onRetryClick()
    }
}