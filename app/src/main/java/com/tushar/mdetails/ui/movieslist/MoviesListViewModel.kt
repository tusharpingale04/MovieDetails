package com.tushar.mdetails.ui.movieslist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tushar.mdetails.data.local.Movie
import com.tushar.mdetails.data.repository.MoviesRepository
import com.tushar.mdetails.data.repository.Resource
import com.tushar.mdetails.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MoviesListViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    /*val isQueryInProgress = MutableLiveData(false)
    val isQueryExhausted = MutableLiveData(false)
    var pageNo = 1*/
    private val _filteredMoviesLiveData = MutableLiveData<List<Movie>>()
    val filteredMoviesLiveData: LiveData<List<Movie>>
        get() = _filteredMoviesLiveData

    private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()
    private val _moviesLiveData = MutableLiveData<Resource<List<Movie>>>()
    val moviesLiveData : LiveData<Resource<List<Movie>>> = Transformations
        .switchMap(moviePageLiveData){ pageNo ->
            viewModelScope.launch {
                repository.fetchNowPlaying(pageNo).collect {
                    _moviesLiveData.value = it
                }
            }
            _moviesLiveData
        }

    /*fun getMovies(pageNo: Int) {
        viewModelScope.launch {
            repository.fetchNowPlaying(pageNo).collect {
                _moviesLiveData.value = it
            }
        }
    }*/

    fun setMoviePage(page: Int) = moviePageLiveData.postValue(page)

    fun isLastPage(): Boolean{
        return _moviesLiveData.value?.data?.size!! % Constants.PAGINATION_PAGE_SIZE != 0
    }

    /*fun getFilteredMovies(query: String) {
        viewModelScope.launch {
            repository.getFilteredMovies(query).collect {
                _filteredMoviesLiveData.value = it
            }
        }
    }

    fun setEmptyFilteredList() {
        _filteredMoviesLiveData.value = null

    }

    fun loadFirstPage() {
        isQueryInProgress.value = true
        isQueryExhausted.value = false
        getMovies()
    }

    fun getPageNumber(): Int {
        return pageNo
    }

    fun loadNextPage() {
        if (!isQueryExhausted.value!! || !isQueryInProgress.value!!) {
            pageNo++
            isQueryInProgress.value = true
            getMovies()
        }
    }*/

}