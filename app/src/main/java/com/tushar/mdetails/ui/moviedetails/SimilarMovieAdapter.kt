package com.tushar.mdetails.ui.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tushar.mdetails.base.BaseAdapter
import com.tushar.mdetails.data.local.Movie
import com.tushar.mdetails.databinding.ItemMovieBinding
import com.tushar.mdetails.ui.movieslist.MovieRowViewModel


class SimilarMovieAdapter : BaseAdapter<Movie>(DIFF_CALLBACK) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val mBinding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewModel = MovieRowViewModel()
        mBinding.vm = viewModel
        return mBinding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        if (binding is ItemMovieBinding) {
            binding.vm?.item?.set(getItem(position))
            binding.executePendingBindings()
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.mId == newItem.mId

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}