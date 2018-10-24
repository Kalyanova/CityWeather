package by.paranoidandroid.cityweather.db.room.dao

import android.arch.persistence.room.*
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast
import io.reactivex.Single

@Dao
interface CityDao {

    @Query("SELECT * FROM city WHERE id=:id")
    fun get(id: Int): Single<RoomForecast>

    @Query("SELECT * FROM city")
    fun getAll(): Single<List<RoomForecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg forecast: RoomForecast)

    @Query("UPDATE city SET " +
            "lon=:lon, lat=:lat, " +
            "temp=:temp, temp_min=:minTemp, temp_max=:maxTemp " +
            "WHERE id=:id")
    fun updateWithoutUrl(id: Int,
                         lon: Double?, lat: Double?,
                         temp: String?, minTemp: String?, maxTemp: String?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecast: RoomForecast)

    @Delete
    fun delete(forecast: RoomForecast)
}