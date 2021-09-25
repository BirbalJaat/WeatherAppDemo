package com.birbal.weatherapp

import android.app.Application
import com.birbal.weatherapp.di.component.ApplicationComponent
import com.birbal.weatherapp.di.component.DaggerApplicationComponent
import com.birbal.weatherapp.di.module.ApplicationModule

class WeatherApp : Application(){

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

    // Needed to replace the component with a test specific one
    fun setComponent(applicationComponent: ApplicationComponent) {
        this.applicationComponent = applicationComponent
    }
}
