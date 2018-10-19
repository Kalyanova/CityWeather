package by.paranoidandroid.cityweather.network.entity

import by.paranoidandroid.cityweather.domain.entity.Coord

data class WebCoord(
        override var lon: Double?,
        override var lat: Double?
) : Coord()