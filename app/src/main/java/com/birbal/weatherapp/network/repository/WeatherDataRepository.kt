package com.birbal.weatherapp.network.repository

import com.birbal.weatherapp.BuildConfig
import com.birbal.weatherapp.network.remote.NetworkService
import com.birbal.weatherapp.network.remote.response.WeatherResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataRepository @Inject constructor(private val networkService: NetworkService) {

    fun featchWeatherDetails(query: String): Single<WeatherResponse>{
      return networkService.getCurrentWeather(BuildConfig.API_KEY, query)
    }

}