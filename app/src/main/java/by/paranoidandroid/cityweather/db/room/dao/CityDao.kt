package by.paranoidandroid.cityweather.db.room.dao

import android.arch.persistence.room.*
import by.paranoidandroid.cityweather.db.room.entity.City

@Dao
interface CityDao {

    @Query("SELECT * FROM city WHERE id=:id")
    fun get(id: Int): City

    @Query("SELECT * FROM city")
    fun getAll(): List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)

    @Delete
    fun delete(city: City)
}