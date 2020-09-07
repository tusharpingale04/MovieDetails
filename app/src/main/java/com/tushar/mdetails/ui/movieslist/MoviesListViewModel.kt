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

/**
 *
 */
@ExperimentalCoroutinesApi
class MoviesListViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<Resource<List<Movie>>>()
    val moviesLiveData: LiveData<Resource<List<Movie>>>
        get() = _moviesLiveData

    private val _filteredMoviesLiveData = MutableLiveData<List<Movie>>()
    val filteredMoviesLiveData: LiveData<List<Movie>>
        get() = _filteredMoviesLiveData

    /**
     * Fetches Now Playing from the API or DB
     */
    fun getNowPlaying() {
        viewModelScope.launch {
            repository.fetchNowPlaying(1).collect {
                _moviesLiveData.value = it
            }
        }
    }

    /**
     * Fetched filtered record from the DB
     * @param query is pass to filter the movie list as per query
     */
    fun getFilteredMovies(query: String) {
        viewModelScope.launch {
            repository.getFilteredMovies(query).collect {
                _filteredMoviesLiveData.value = it
            }
        }
    }

    /**
     * sets empty filter data list
     */
    fun setEmptyFilteredList() {
        _filteredMoviesLiveData.value = null

    }

}