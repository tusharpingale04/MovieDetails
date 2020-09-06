package com.tushar.mdetails.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tushar.mdetails.data.local.Query
import com.tushar.mdetails.data.local.RecentSearchDao
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel @ViewModelInject constructor(
    private val dao: RecentSearchDao
): ViewModel() {

    val showSearchBox = MutableLiveData(false)
    val searchQuery = MutableLiveData("")

    private val _queriesLiveData = MutableLiveData<List<Query>>()

    val queriesLiveData: LiveData<List<Query>>
        get() = _queriesLiveData


    fun getRecentQueries() {
        viewModelScope.launch {
            dao.getAllSearchQueries().collect{
                _queriesLiveData.value = it
            }
        }
    }

    fun insertQuery(query: String){
        viewModelScope.launch {
            dao.insert(
                Query(
                    query = query
                )
            )
        }
    }

    fun deleteQueries(){
        viewModelScope.launch {
            dao.deleteAdditionalQueries()
        }
    }
}