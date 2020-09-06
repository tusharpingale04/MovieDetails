package com.tushar.mdetails.ui.movieslist

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tushar.mdetails.data.local.Movie

class MovieRowViewModel : ViewModel(){

    val item = ObservableField<Movie>()

}