package com.birbal.weatherapp.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.birbal.weatherapp.network.repository.WeatherDataRepository
import com.birbal.weatherapp.ui.base.BaseActivity
import com.birbal.weatherapp.ui.home_page.HomePageViewModel
import com.birbal.weatherapp.utils.ViewModelProviderFactory
import com.birbal.weatherapp.utils.network.NetworkHelper
import com.birbal.weatherapp.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import java.util.EnumSet.of

/**
 * Kotlin Generics Reference: https://kotlinlang.org/docs/reference/generics.html
 * Basically it means that we can pass any class that extends BaseActivity which take
 * BaseViewModel subclass as parameter
 */
@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    fun provideHomePageViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        weatherDataRepository: WeatherDataRepository
    ): HomePageViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(HomePageViewModel::class) {
            HomePageViewModel(schedulerProvider, compositeDisposable, networkHelper, weatherDataRepository)
        }).get(HomePageViewModel::class.java)


}