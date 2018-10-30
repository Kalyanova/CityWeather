package by.paranoidandroid.cityweather.db.room.entity

import android.arch.persistence.room.ColumnInfo
import by.paranoidandroid.cityweather.domain.entity.Main

data class RoomMain(
    override var temp: String?,
    @ColumnInfo(name = "temp_min")
    override var minTemp: String?,
    @ColumnInfo(name = "temp_max")
    override var maxTemp: String?
) : Main()
