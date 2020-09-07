package com.tushar.mdetails.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.tushar.mdetails.R
import com.tushar.mdetails.data.local.Movie
import com.tushar.mdetails.data.repository.Resource
import com.tushar.mdetails.databinding.MovieDetailsFragmentBinding
import com.tushar.mdetails.di.MovieScope
import com.tushar.mdetails.extensions.hide
import com.tushar.mdetails.extensions.show
import com.tushar.mdetails.ui.MainActivityViewModel
import com.tushar.mdetails.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * This Fragment consist of details regarding the movies
 * Field inject as per scope wise is performed
 * Fetches Cast, Crew and Similar Movies
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailsFragment()
    }

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: MovieDetailsFragmentBinding
    private lateinit var bundle: Bundle

    @MovieScope
    @Inject
    lateinit var castAdapter : CastAdapter

    @MovieScope
    @Inject
    lateinit var crewAdapter :CrewAdapter

    @MovieScope
    @Inject
    lateinit var similarMovieAdapter : SimilarMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = requireArguments()
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

    }

    //Shared ViewModel to pass data between fragments
    private val cachedVm : MainActivityViewModel by lazy {
        activity?.run {
            ViewModelProvider(this).get(MainActivityViewModel::class.java)
        } ?: throw IllegalArgumentException("Activity Not Found")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_details_fragment, container, false)
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            val id = bundle.getParcelable<Movie>(Constants.MOVIE)!!.movieId
            movie.value = bundle.getParcelable(Constants.MOVIE)
            loadCastAndCrew(id)
            loadSimilarMovie(id)
        }
        binding.imageBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        subscribeObservers()
        attachAdapters()
    }

    private fun attachAdapters() {
        if (binding.recyclerCast.adapter == null) {
            binding.recyclerCast.adapter = castAdapter
        }
        if (binding.recyclerCrew.adapter == null) {
            binding.recyclerCrew.adapter = crewAdapter
        }
        if (binding.recyclerSimilarMovie.adapter == null) {
            binding.recyclerSimilarMovie.adapter = similarMovieAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.similarMoviesLiveData.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    showLoading(true)
                }
                Resource.Status.SUCCESS -> {
                    showLoading(false)
                    if (!resource.data?.results.isNullOrEmpty()) {
                        similarMovieAdapter.submitList(resource.data!!.results!!.toMutableList())
                    }else{
                        binding.textSimilarMovie.hide()
                        binding.recyclerSimilarMovie.hide()
                    }
                }
                Resource.Status.ERROR -> {
                    showLoading(false)
                    resource?.message?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        viewModel.castLiveData.observe(viewLifecycleOwner, Observer {resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    showLoading(true)
                }
                Resource.Status.SUCCESS -> {
                    showLoading(false)
                    if (!resource.data?.cast.isNullOrEmpty()) {
                        castAdapter.submitList(resource.data!!.cast!!.toMutableList())
                    }else{
                        binding.textCast.hide()
                        binding.recyclerCast.hide()
                    }
                    if (!resource.data?.crew.isNullOrEmpty()) {
                        crewAdapter.submitList(resource.data!!.crew!!.toMutableList())
                    }else{
                        binding.textCrew.hide()
                        binding.recyclerCrew.hide()
                    }
                }
                Resource.Status.ERROR -> {
                    showLoading(false)
                    resource?.message?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    /**
     * This method is used for Showing/Hiding progress
     * @param isLoading is used for hide show progress
     */
    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.progressBar.show()
        }else{
            binding.progressBar.hide()
        }

    }

    override fun onResume() {
        super.onResume()
        cachedVm.showSearchBox.value = false
    }

}