package com.birbal.weatherapp.network.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @Expose
    @SerializedName("current")
    val current: Current,

    @Expose
    @SerializedName("location")
    val location: Location,

    @Expose
    @SerializedName("request")
    val request: Request
){
    data class Request(
        @Expose
        @SerializedName("language")
        val language: String,

        @Expose
        @SerializedName("query")
        val query: String,

        @Expose
        @SerializedName("type")
        val type: String,

        @Expose
        @SerializedName("unit")
        val unit: String
    )

    data class Location(

        @Expose
        @SerializedName("country")
        val country: String,

        @Expose
        @SerializedName("lat")
        val lat: String,

        @Expose
        @SerializedName("localtime")
        val localtime: String,

        @Expose
        @SerializedName("localtime_epoch")
        val localtime_epoch: Int,

        @Expose
        @SerializedName("lon")
        val lon: String,

        @Expose
        @SerializedName("name")
        val name: String,

        @Expose
        @SerializedName("region")
        val region: String,

        @Expose
        @SerializedName("timezone_id")
        val timezone_id: String,

        @Expose
        @SerializedName("utc_offset")
        val utc_offset: String

    )

    data class Current(

        @Expose
        @SerializedName("cloudcover")
        val cloudcover: Int,

        @Expose
        @SerializedName("feelslike")
        val feelslike: Int,

        @Expose
        @SerializedName("humidity")
        val humidity: Int,

        @Expose
        @SerializedName("is_day")
        val is_day: String,

        @Expose
        @SerializedName("observation_time")
        val observation_time: String,

        @Expose
        @SerializedName("precip")
        val precip: Double,

        @Expose
        @SerializedName("pressure")
        val pressure: Int,

        @Expose
        @SerializedName("temperature")
        val temperature: Int,

        @Expose
        @SerializedName("uv_index")
        val uv_index: Int,

        @Expose
        @SerializedName("visibility")
        val visibility: Int,

        @Expose
        @SerializedName("weather_code")
        val weather_code: Int,

        @Expose
        @SerializedName("weather_descriptions")
        val weather_descriptions: List<String>,

        @Expose
        @SerializedName("weather_icons")
        val weather_icons: List<String>,

        @Expose
        @SerializedName("wind_degree")
        val wind_degree: Int,

        @Expose
        @SerializedName("wind_dir")
        val wind_dir: String,

        @Expose
        @SerializedName("wind_speed")
        val wind_speed: Int
    )
}