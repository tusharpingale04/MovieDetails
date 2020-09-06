package com.tushar.mdetails.ui.moviedetails

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tushar.mdetails.data.remote.Crew

class CrewViewModel : ViewModel(){

    val item = ObservableField<Crew>()
}