package by.paranoidandroid.cityweather.db.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import by.paranoidandroid.cityweather.domain.entity.CityForecast

@Entity(tableName = "city")
class CityForecastRoom(@PrimaryKey(autoGenerate = true) var cityId: Int,
                name: String,
                var country: String?,
                var temperature: String?,
                @ColumnInfo(name = " temp_min") var minTemp: String?,
                @ColumnInfo(name = " temp_max") var maxTemp: String?,
                url: String?): CityForecast(cityId, name, null, url)