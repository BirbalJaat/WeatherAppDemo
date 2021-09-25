package com.birbal.weatherapp.ui.home_page

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.birbal.weatherapp.R
import com.birbal.weatherapp.di.component.ActivityComponent
import com.birbal.weatherapp.ui.base.BaseActivity
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import android.location.Geocoder
import android.view.View
import com.birbal.weatherapp.utils.common.LocaleHelper.checkPermissions
import com.birbal.weatherapp.utils.common.LocaleHelper.isLocationEnabled
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_input_part.*
import kotlinx.android.synthetic.main.layout_weather_additional_info.*
import kotlinx.android.synthetic.main.layout_weather_basic_info.*
import java.util.*
import androidx.appcompat.app.AppCompatDelegate





class MainActivity : BaseActivity<HomePageViewModel>() {

    companion object {
        const val TAG = "MainActivity"
    }

    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {

        btn_change_theme.setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.weatherData.observe(this, Observer {
            if (it != null) {
                tv_error_message.visibility = View.GONE
                tv_date_time?.text = it.location.localtime
                tv_temperature?.text = it.current.temperature.toString()
                tv_city_country?.text =
                    "${it.location.name} ,${it.location.region} (${it.location.country})"
                Glide.with(this).load(it.current.weather_icons[0]).into(iv_weather_condition)
                tv_weather_condition?.text = it.current.weather_descriptions[0]
                tv_humidity_value?.text = it.current.humidity.toString()
                tv_pressure_value?.text = it.current.pressure.toString()
                tv_visibility_value?.text = it.current.visibility.toString()
                progressBar.visibility = View.GONE
            } else {
                tv_error_message.visibility = View.VISIBLE
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions(this)) {
            if (isLocationEnabled(this)) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        getCurrentCity(location.latitude, location.longitude)
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            print("mLastLocation Latitude : ${mLastLocation.latitude}  & mLastLocation Longitude ${mLastLocation.longitude}")
            getCurrentCity(mLastLocation.latitude, mLastLocation.longitude)
//            tv_humidity.text = "${mLastLocation.latitude}"
//            tv_time_zone.text = "${mLastLocation.longitude}"
        }
    }

    fun getCurrentCity(latitude: Double, longitute: Double) {
        var geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address> = geocoder.getFromLocation(latitude, longitute, 1)
        var cityName: String = addresses[0].getAddressLine(0)

        viewModel.getWeatherDetails(cityName.trim())
    }

}