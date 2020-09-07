package com.tushar.mdetails.ui.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tushar.mdetails.base.BaseAdapter
import com.tushar.mdetails.data.remote.Crew
import com.tushar.mdetails.databinding.ItemCrewBinding

/**
 * Adapter class to inflate crew items in movie details screen
 */
class CrewAdapter : BaseAdapter<Crew>(DIFF_CALLBACK) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val mBinding = ItemCrewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewModel = CrewViewModel()
        mBinding.vm = viewModel
        return mBinding

    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        if (binding is ItemCrewBinding) {
            binding.vm?.item?.set(getItem(position))
            binding.executePendingBindings()
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Crew>() {
            override fun areItemsTheSame(oldItem: Crew, newItem: Crew): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Crew, newItem: Crew): Boolean =
                oldItem == newItem
        }
    }
}