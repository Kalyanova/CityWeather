package by.paranoidandroid.cityweather.db.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import by.paranoidandroid.cityweather.domain.entity.CityList
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main

data class RoomCityList(
        override var cnt: Int,
        override var list: Array<RoomForecast>
) : CityList<RoomForecast>()

@Entity(tableName = "city")
data class RoomForecast(
        @PrimaryKey(autoGenerate = true)
        override var id: Int,
        override var name: String,
        @Embedded
        override var coord: RoomCoord?,
        @Embedded
        override var main: RoomMain?,
        override var url: String?
) : Forecast<RoomMain, RoomCoord>()

data class RoomMain(
        override var temp: String?,
        @ColumnInfo(name = "temp_min")
        override var minTemp: String?,
        @ColumnInfo(name = "temp_max")
        override var maxTemp: String?
) : Main()

data class RoomCoord(
        override var lon: Double?,
        override var lat: Double?
) : Coord()