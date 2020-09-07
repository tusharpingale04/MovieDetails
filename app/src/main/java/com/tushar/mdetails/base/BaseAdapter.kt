package com.tushar.mdetails.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tushar.mdetails.R

/**
 * Base Adapter to reduce code redundancy into the adapter class
 * This adapter accepts a Diff utils object to make list changes faster by object id of old and new list object
 */
abstract class BaseAdapter<T : Any>(callback: DiffUtil.ItemCallback<T>) : ListAdapter<T, BaseViewHolder<ViewDataBinding>>(callback) {

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        (holder as BaseViewHolder<*>).binding.root.setTag(R.string.position, position)
        bind(holder.binding, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = getViewHolder(parent, viewType)

    open fun getViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(createBinding(parent, viewType))

    abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    protected abstract fun bind(binding: ViewDataBinding, position: Int)
}