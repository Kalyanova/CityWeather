package by.paranoidandroid.cityweather.network.entity

import by.paranoidandroid.cityweather.domain.entity.Main
import com.google.gson.annotations.SerializedName

data class WebMain(
        override var temp: String?,
        @SerializedName("temp_min")
        override var minTemp: String?,
        @SerializedName("temp_max")
        override var maxTemp: String?
) : Main()
