package com.ujjwalsingh.kotlinmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ujjwalsingh.kotlinmvvm.di.DaggerApiComponent
import com.ujjwalsingh.kotlinmvvm.model.CountriesService
import com.ujjwalsingh.kotlinmvvm.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel : ViewModel() {
    @Inject
    lateinit var countriesService : CountriesService
    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposal = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }


    private fun fetchCountries() {
        loading.value = true
        disposal.add(
            countriesService.getCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableSingleObserver<List<Country>>() {
                override fun onSuccess(countryList: List<Country>) {
                    countries.value=countryList
                    countryLoadError.value=false
                    loading.value=false
                }

                override fun onError(e: Throwable) {
                    countryLoadError.value=true
                    loading.value=false
                }

            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposal.clear()
    }
}