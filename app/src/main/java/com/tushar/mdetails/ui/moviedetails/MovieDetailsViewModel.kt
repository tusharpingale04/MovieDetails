package com.tushar.mdetails.ui.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tushar.mdetails.data.local.Movie
import com.tushar.mdetails.data.remote.GetCastAndCrewResponse
import com.tushar.mdetails.data.remote.GetMovieListResponse
import com.tushar.mdetails.data.repository.MovieDetailsRepository
import com.tushar.mdetails.data.repository.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel which acts as an intermediate between view and data(Network or DB)
 * @param repository is the instance of [MovieDetailsRepository] to fetch data from DB or Network
 */
@ExperimentalCoroutinesApi
class MovieDetailsViewModel @ViewModelInject constructor(
    val repository: MovieDetailsRepository
) : ViewModel() {

    val movie = MutableLiveData<Movie>()
    private val _similarMoviesLiveData = MutableLiveData<Resource<GetMovieListResponse>>()
    val similarMoviesLiveData: LiveData<Resource<GetMovieListResponse>>
        get() = _similarMoviesLiveData

    private val _castLiveData = MutableLiveData<Resource<GetCastAndCrewResponse>>()
    val castLiveData: LiveData<Resource<GetCastAndCrewResponse>>
        get() = _castLiveData

    /**
     * Loads Cast and Crew from the Api
     * @param movieId is the id of the movie whose details are to be loaded
     */
    fun loadCastAndCrew(movieId: String) {
        viewModelScope.launch {
            repository.getCrewAndCast(movieId).collect {
                _castLiveData.value = it
            }
        }
    }

    /**
     * Loads Similar Movies from the Api
     * @param movieId is the id of the movie whose details are to be loaded
     */
    fun loadSimilarMovie(movieId: String) {
        viewModelScope.launch {
            repository.getSimilarMovies(movieId).collect {
                _similarMoviesLiveData.value = it
            }
        }
    }

}