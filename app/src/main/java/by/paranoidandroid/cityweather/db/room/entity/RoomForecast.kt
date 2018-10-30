package by.paranoidandroid.cityweather.db.room.entity

import android.arch.persistence.room.*
import by.paranoidandroid.cityweather.domain.entity.Forecast
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "city",
    indices = [Index(value = ["name"], unique = true)]
)
data class RoomForecast(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    override var id: Int,
    override var name: String,
    @ColumnInfo(name = "city_description")
    @SerializedName("city_description")
    override var cityDescription: String?,
    @Embedded
    override var coord: RoomCoord?,
    @Embedded
    override var main: RoomMain?,
    override var url: String?
) : Forecast<RoomMain, RoomCoord>()
