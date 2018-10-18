package by.paranoidandroid.cityweather.network.entity

import by.paranoidandroid.cityweather.domain.entity.CityList

data class WebCityList(
        override var cnt: Int,
        override var list: Array<WebForecast>
) : CityList<WebForecast>()