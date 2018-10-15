package by.paranoidandroid.cityweather.db.room.dao

import android.arch.persistence.room.*
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast

@Dao
interface CityDao {

    @Query("SELECT * FROM city WHERE id=:id")
    fun get(id: Int): RoomForecast

    @Query("SELECT * FROM city")
    fun getAll(): List<RoomForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg city: RoomForecast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: RoomForecast)

    @Delete
    fun delete(city: RoomForecast)
}