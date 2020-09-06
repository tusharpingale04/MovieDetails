package com.tushar.mdetails.ui.moviedetails

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tushar.mdetails.data.remote.Cast

class CastViewModel : ViewModel(){
    val item = ObservableField<Cast>()
}