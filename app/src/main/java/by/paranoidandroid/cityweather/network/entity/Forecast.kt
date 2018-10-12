package by.paranoidandroid.cityweather.network.entity

import com.google.gson.annotations.SerializedName

data class CityList(
        val cnt: Int,
        val list: Array<Forecast>
)

data class Forecast(
        var id: Int,
        var main: Main? = null
)

data class Main(
        val temp: String,
        @SerializedName("temp_min")
        val minTemp: String,
        @SerializedName("temp_max")
        val maxTemp: String
)