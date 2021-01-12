package com.ujjwalsingh.kotlinmvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ujjwalsingh.kotlinmvvm.model.CountriesService
import com.ujjwalsingh.kotlinmvvm.model.Country
import com.ujjwalsingh.kotlinmvvm.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countriesService : CountriesService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var textSingle : Single<List<Country>>? = null

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCountriesSuccess(){
        val country = Country("India","Delhi","url")
        val countryList = arrayListOf<Country>(country)
        textSingle = Single.just(countryList)
        `when`(countriesService.getCountries()).thenReturn(textSingle)
        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.countries.value?.size)
        Assert.assertEquals(true, listViewModel.loading.value)
        Assert.assertEquals(false, listViewModel.countryLoadError.value)

    }
    @Test
    fun getCountriesFailed(){
        textSingle = Single.error(Throwable())
        `when`(countriesService.getCountries()).thenReturn(textSingle)
        listViewModel.refresh()

        Assert.assertEquals(false, listViewModel.loading.value)
        Assert.assertEquals(true, listViewModel.countryLoadError.value)

    }


    @Before
    fun setUpRxSchedulers(){
        val immediate = object : Scheduler(){
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor {it.run() })
            }

        }

        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }
}