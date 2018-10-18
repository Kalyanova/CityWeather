package by.paranoidandroid.cityweather.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import by.paranoidandroid.cityweather.db.room.dao.CityDao
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast

@Database(entities = [RoomForecast::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        const val DATABASE_NAME = "cities.db"
    }
}