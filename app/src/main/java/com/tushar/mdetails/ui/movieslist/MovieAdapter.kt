package com.tushar.mdetails.ui.movieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tushar.mdetails.base.BaseAdapter
import com.tushar.mdetails.data.local.Movie
import com.tushar.mdetails.databinding.ItemMovieBinding
import com.tushar.mdetails.databinding.RowNoMoreResultsBinding
import com.tushar.mdetails.ui.adapter.NoMoreViewModel


class MovieAdapter(private val callBack: (Movie) -> Unit) : BaseAdapter<Movie>(DIFF_CALLBACK) {

    private val noMovieModel = Movie(
        NO_MORE_RESULTS,
        "",
        "",
        "",
        0.0,
        "",
        "",
        "",
        0.0,
        0L,
        0L
    )

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        when (viewType) {
            MOVIE_ITEM -> {
                val mBinding = ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                val viewModel = MovieRowViewModel()
                mBinding.vm = viewModel
                mBinding.rootView.setOnClickListener {
                    mBinding.vm?.item?.get()?.let { callBack.invoke(it) }
                }
                return mBinding
            }
            NO_MORE_RESULTS -> {
                val mBinding = RowNoMoreResultsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                val viewModel = NoMoreViewModel()
                mBinding.vm = viewModel
                return mBinding
            }
            else -> {
                throw IllegalArgumentException("No View Type Found: $viewType")
            }
        }
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        if(binding is ItemMovieBinding){
            binding.vm?.item?.set(getItem(position))
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (currentList[position].mId > -1) {
            return MOVIE_ITEM
        }
        return NO_MORE_RESULTS
    }

    companion object {

        const val NO_MORE_RESULTS = -1
        const val MOVIE_ITEM = 0

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.mId == newItem.mId

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    fun submitList(movieList: List<Movie>?, isQueryExhausted: Boolean) {
        val newList = movieList?.toMutableList()
        if (isQueryExhausted)
            newList?.add(noMovieModel)
        submitList(newList)
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].mId.toLong()
    }
}