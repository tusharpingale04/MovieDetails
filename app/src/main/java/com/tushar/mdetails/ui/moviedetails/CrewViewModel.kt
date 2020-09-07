package com.tushar.mdetails.ui.moviedetails

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tushar.mdetails.data.remote.Crew

/**
 * This ViewModel is used for data binding of value in item_crew layout
 */
class CrewViewModel : ViewModel(){

    val item = ObservableField<Crew>()
}