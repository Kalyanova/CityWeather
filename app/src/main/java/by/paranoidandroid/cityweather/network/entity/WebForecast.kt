package by.paranoidandroid.cityweather.network.entity

import by.paranoidandroid.cityweather.domain.entity.CityList
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import com.google.gson.annotations.SerializedName

data class WebCityList(
        override var cnt: Int,
        override var list: Array<WebForecast>
) : CityList<WebForecast>()

data class WebForecast(
        override var id: Int,
        override var name: String,
        override var coord: WebCoord?,
        override var main: WebMain?,
        override var url: String?
) : Forecast<WebMain, WebCoord>()

data class WebMain(
        override var temp: String?,
        @SerializedName("temp_min")
        override var minTemp: String?,
        @SerializedName("temp_max")
        override var maxTemp: String?
) : Main()

data class WebCoord(
        override var lon: Double?,
        override var lat: Double?
) : Coord()