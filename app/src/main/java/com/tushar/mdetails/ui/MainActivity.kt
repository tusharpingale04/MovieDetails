package com.tushar.mdetails.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import com.tushar.mdetails.R
import com.tushar.mdetails.base.BaseActivity
import com.tushar.mdetails.data.local.Query
import com.tushar.mdetails.databinding.ActivityMainBinding
import com.tushar.mdetails.extensions.hide
import com.tushar.mdetails.extensions.replaceFragment
import com.tushar.mdetails.extensions.show
import com.tushar.mdetails.extensions.showToast
import com.tushar.mdetails.ui.movieslist.MoviesListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        if (savedInstanceState == null) {
            replaceFragment(
                MoviesListFragment.newInstance(),
                R.id.container
            )
        }
        mViewBinding.lifecycleOwner = this
        mViewModel.showSearchBox.observe(this, {
            if (it) {
                mViewBinding.searchBar.show()
            } else {
                mViewBinding.searchBar.hide()
            }
        })
        setUpAutoCompleteView()
        setUpSearch()
        initObservers()
    }

    private fun initObservers() {
        mViewModel.queriesLiveData.observe(this, {
            refreshAdapter(it)
        })
    }

    private fun refreshAdapter(queries: List<Query>?) {
        adapter.clear()
        queries?.forEach {
            adapter.add(it.query)
        }
        adapter.notifyDataSetChanged()
    }

    private fun setUpSearch() {
        mViewBinding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                /*if(textView.text.isNotEmpty()){
                    mViewModel.insertQuery(textView.text.toString())
                    mViewModel.getRecentQueries()
                    mViewModel.deleteQueries()
                }*/
                mViewModel.searchQuery.value = textView.text.toString()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        mViewBinding.btClear.setOnClickListener {
            mViewBinding.etSearch.setText("")
            mViewModel.searchQuery.value = ""
        }
    }

    private fun hideKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setUpAutoCompleteView() {
        val arrayList = arrayListOf<String>()
        mViewModel.queriesLiveData.value?.let { queries ->
            queries.forEach {
                arrayList.add(it.query)
            }
        } ?: emptyList<String>()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            arrayList
        )
        mViewBinding.etSearch.setAdapter(adapter)
        mViewBinding.etSearch.setOnClickListener {
            (it as AutoCompleteTextView).showDropDown()
        }
        mViewBinding.etSearch.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                (v as AutoCompleteTextView).showDropDown()
            }
        }
    }


    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override val mViewModel: MainActivityViewModel
        get() = ViewModelProvider(this).get(MainActivityViewModel::class.java)
}