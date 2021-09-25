package com.birbal.weatherapp.ui.home_page

import androidx.lifecycle.MutableLiveData
import com.birbal.weatherapp.network.remote.response.WeatherResponse
import com.birbal.weatherapp.network.repository.WeatherDataRepository
import com.birbal.weatherapp.ui.base.BaseViewModel
import com.birbal.weatherapp.utils.network.NetworkHelper
import com.birbal.weatherapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


class HomePageViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val weatherDataRepository: WeatherDataRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {


    val weatherData: MutableLiveData<WeatherResponse> = MutableLiveData()

    override fun onCreate() {}

    fun getWeatherDetails(query: String) {
        if (networkHelper.isNetworkConnected()) {
            compositeDisposable.addAll(
                weatherDataRepository.featchWeatherDetails(query)
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                            weatherData.postValue(it)
                            print(it.toString())
                        },
                        {
                            print(it.toString())
                        }
                    )
            )
        }
    }
}