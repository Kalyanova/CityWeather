package by.paranoidandroid.cityweather.db.room.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import by.paranoidandroid.cityweather.domain.entity.Forecast

@Entity(tableName = "city")
data class RoomForecast(
        @PrimaryKey(autoGenerate = true)
        override var id: Int,
        override var name: String,
        //var description: String?,
        @Embedded
        override var coord: RoomCoord?,
        @Embedded
        override var main: RoomMain?,
        override var url: String?
) : Forecast<RoomMain, RoomCoord>()
