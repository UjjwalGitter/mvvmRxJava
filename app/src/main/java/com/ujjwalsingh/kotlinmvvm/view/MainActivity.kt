package com.ujjwalsingh.kotlinmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ujjwalsingh.kotlinmvvm.R
import com.ujjwalsingh.kotlinmvvm.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    private var countriesAdapter = CountryListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing=false
            viewModel.refresh()
        }
        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.countries.observe(this, Observer { countries ->
            countries?.let {
                recyclerView.visibility = View.VISIBLE
                countriesAdapter.updateList(it) }
        })
        viewModel.countryLoadError.observe(this, Observer { isError ->
            isError?.let { list_error.visibility = if(it) View.VISIBLE else View.GONE }
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let { progress_circular.visibility = if(it) View.VISIBLE else View.GONE

                if(it){
                    list_error.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
            }
        })
    }
}