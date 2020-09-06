package com.tushar.mdetails.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tushar.mdetails.data.local.Movie
import com.tushar.mdetails.data.repository.Resource
import com.tushar.mdetails.extensions.bindResource
import com.tushar.mdetails.pagination.RecyclerViewPaginator
import com.tushar.mdetails.ui.movieslist.MovieAdapter
import com.tushar.mdetails.ui.movieslist.MoviesListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@BindingAdapter("adapter")
fun bindRecyclerViewAdapter(view: RecyclerView, adapter: MovieAdapter) {
    view.adapter = adapter
}

@BindingAdapter("adapterMovieList")
fun bindAdapterMovieList(view: RecyclerView, resource: Resource<List<Movie>>?) {
    view.bindResource(resource) {
        val adapter = view.adapter as? MovieAdapter
        resource?.data?.let {
            adapter?.submitList(it)
        }
    }
}

@ExperimentalCoroutinesApi
@BindingAdapter("moviePagination")
fun bindMoviePagination(view: RecyclerView, viewModel: MoviesListViewModel) {
    RecyclerViewPaginator(
            recyclerView = view,
            isLoading = { viewModel.moviesLiveData.value?.status == Resource.Status.LOADING },
            loadMore = { viewModel.setMoviePage(it) },
            onLast = { false }
    ).run {
        currentPage = 1
    }
}