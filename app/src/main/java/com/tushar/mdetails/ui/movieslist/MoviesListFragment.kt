package com.tushar.mdetails.ui.movieslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tushar.mdetails.R
import com.tushar.mdetails.data.repository.Resource
import com.tushar.mdetails.databinding.MoviesListFragmentBinding
import com.tushar.mdetails.extensions.hide
import com.tushar.mdetails.extensions.replaceFragment
import com.tushar.mdetails.extensions.show
import com.tushar.mdetails.ui.MainActivityViewModel
import com.tushar.mdetails.ui.moviedetails.MovieDetailsFragment
import com.tushar.mdetails.utils.RecyclerViewPaginator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.IllegalArgumentException

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    companion object {
        fun newInstance() = MoviesListFragment()
    }

    private val viewModel: MoviesListViewModel by lazy {
        ViewModelProvider(this).get(MoviesListViewModel::class.java)
    }

    private val cachedVm : MainActivityViewModel by lazy {
        activity?.run {
            ViewModelProvider(this).get(MainActivityViewModel::class.java)
        } ?: throw IllegalArgumentException("Activity Not Found")
    }

    private lateinit var binding: MoviesListFragmentBinding
    private  val adapter: MovieAdapter by lazy {
        MovieAdapter {
            cachedVm.insertQuery(it.originalTitle)
            cachedVm.getRecentQueries()
            cachedVm.deleteQueries()
            replaceFragment(requireActivity() as AppCompatActivity, MovieDetailsFragment.newInstance(),R.id.container, "MovieDetailsFragment")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_list_fragment, container, false)
        binding.viewModel = viewModel
        binding.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        initObservers()
        viewModel.setMoviePage(1)
    }

    private fun initRecyclerView() {
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovies.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        binding.rvMovies.addOnScrollListener(object : RecyclerViewPaginator(binding.rvMovies) {
            override val isLastPage: Boolean
                get() = viewModel.isLastPage()


            override fun loadMore(nextPage: Int) {
                viewModel.setMoviePage(nextPage)
            }
        })
        /*binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount.minus(1) && cachedVm.searchQuery.value.isNullOrEmpty()) {
                    viewModel.loadNextPage()
                }
            }
        })*/
        binding.rvMovies.adapter = adapter
    }

    private fun initObservers() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    showLoading(true)
                }
                Resource.Status.SUCCESS -> {
                    showLoading(false)
                    if (!resource.data.isNullOrEmpty()) {
                        adapter.submitList(resource.data.toMutableList())
                    }
                }
                Resource.Status.ERROR -> {
                    showLoading(false)
                    resource?.message?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        /*cachedVm.searchQuery.observe(viewLifecycleOwner, {
            if(!it.isNullOrEmpty()){
                viewModel.getFilteredMovies(it)
            }else{
                viewModel.setEmptyFilteredList()
            }
        })

        viewModel.filteredMoviesLiveData.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it,true)
            } ?: setDefaultList()
        })*/
    }

    private fun setDefaultList() {
        //viewModel.loadFirstPage()
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.progressBar.show()
        }else{
            binding.progressBar.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        cachedVm.showSearchBox.value = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMovies.adapter = null
    }
}