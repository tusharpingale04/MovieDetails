package com.tushar.mdetails.ui.movieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tushar.mdetails.base.BaseAdapter
import com.tushar.mdetails.data.local.Movie
import com.tushar.mdetails.databinding.ItemMovieBinding
/**
 * Adapter class to inflate Movies items in Movie List Screen
 * @param callBack is a higher order function to provide callback to the fragment on item click
 */
class MovieAdapter(private val callBack: (Movie, ImageView) -> Unit) : BaseAdapter<Movie>(DIFF_CALLBACK) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val mBinding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewModel = MovieRowViewModel()
        mBinding.vm = viewModel
        mBinding.btnBookNow.setOnClickListener {
            mBinding.vm?.item?.get()?.let { callBack.invoke(it, mBinding.image) }
        }
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