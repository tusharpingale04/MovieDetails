package com.tushar.mdetails.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tushar.mdetails.R
import com.tushar.mdetails.ui.MainActivityViewModel
import java.lang.IllegalArgumentException

class MovieDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailsFragment()
    }

    private lateinit var viewModel: MovieDetailsViewModel

    private val cachedVm : MainActivityViewModel by lazy {
        activity?.run {
            ViewModelProvider(this).get(MainActivityViewModel::class.java)
        } ?: throw IllegalArgumentException("Activity Not Found")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        cachedVm.showSearchBox.value = false
    }

}