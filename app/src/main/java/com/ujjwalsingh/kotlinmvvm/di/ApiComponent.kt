package com.ujjwalsingh.kotlinmvvm.di

import com.ujjwalsingh.kotlinmvvm.model.CountriesService
import com.ujjwalsingh.kotlinmvvm.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: CountriesService)
    fun inject(viewModel: ListViewModel)
}