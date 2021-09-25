package com.birbal.weatherapp.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.birbal.weatherapp.BuildConfig
import com.birbal.weatherapp.WeatherApp
import com.birbal.weatherapp.di.ApplicationContext
import com.birbal.weatherapp.network.remote.NetworkService
import com.birbal.weatherapp.network.remote.Networking
import com.birbal.weatherapp.network.remote.Networking.API_KEY
import com.birbal.weatherapp.utils.network.NetworkHelper
import com.birbal.weatherapp.utils.rx.RxSchedulerProvider
import com.birbal.weatherapp.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: WeatherApp) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
        Networking.create(
            BuildConfig.API_KEY,
            BuildConfig.BASE_URL// 10MB
        )

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

}