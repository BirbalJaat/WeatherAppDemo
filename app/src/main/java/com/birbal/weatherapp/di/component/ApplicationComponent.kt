package com.birbal.weatherapp.di.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.birbal.weatherapp.WeatherApp
import com.birbal.weatherapp.di.ApplicationContext
import com.birbal.weatherapp.di.module.ApplicationModule
import com.birbal.weatherapp.network.remote.NetworkService
import com.birbal.weatherapp.network.repository.WeatherDataRepository
import com.birbal.weatherapp.utils.network.NetworkHelper
import com.birbal.weatherapp.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: WeatherApp)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getNetworkHelper(): NetworkHelper

    fun getWeatherDataRepository(): WeatherDataRepository

    fun getSchedulerProvider(): SchedulerProvider

    fun getCompositeDisposable(): CompositeDisposable
}