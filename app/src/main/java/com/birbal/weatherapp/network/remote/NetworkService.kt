package com.birbal.weatherapp.network.remote

import com.birbal.weatherapp.network.remote.response.WeatherResponse
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @POST(Endpoints.CURRENT_WEATHER)
    fun getCurrentWeather(
        @Query(ApiUtils.ACCESSKEY) accessToken : String,
        @Query(ApiUtils.QUERY) query : String,
    ): Single<WeatherResponse>
}