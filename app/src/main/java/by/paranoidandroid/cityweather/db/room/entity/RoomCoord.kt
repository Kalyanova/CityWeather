package by.paranoidandroid.cityweather.db.room.entity

import by.paranoidandroid.cityweather.domain.entity.Coord

data class RoomCoord(
    override var lon: Double?,
    override var lat: Double?
) : Coord()