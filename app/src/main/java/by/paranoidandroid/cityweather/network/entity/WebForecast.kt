package by.paranoidandroid.cityweather.network.entity

import by.paranoidandroid.cityweather.domain.entity.Forecast

data class WebForecast(
        override var id: Int,
        override var name: String,
        override var description: String?,
        override var coord: WebCoord?,
        override var main: WebMain?,
        override var url: String?
) : Forecast<WebMain, WebCoord>()


