package com.birbal.weatherapp.di.component

import com.birbal.weatherapp.di.ActivityScope
import com.birbal.weatherapp.di.module.ActivityModule
import com.birbal.weatherapp.ui.home_page.MainActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(activity: MainActivity)

}