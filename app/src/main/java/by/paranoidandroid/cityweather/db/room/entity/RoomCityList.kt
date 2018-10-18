package by.paranoidandroid.cityweather.db.room.entity

import by.paranoidandroid.cityweather.domain.entity.CityList

data class RoomCityList(
        override var cnt: Int,
        override var list: Array<RoomForecast>
) : CityList<RoomForecast>()
