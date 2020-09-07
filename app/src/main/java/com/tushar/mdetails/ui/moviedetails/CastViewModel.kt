package com.tushar.mdetails.ui.moviedetails

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tushar.mdetails.data.remote.Cast

/**
 * This ViewModel is used for data binding of value in item_cast layout
 */
class CastViewModel : ViewModel(){
    val item = ObservableField<Cast>()
}