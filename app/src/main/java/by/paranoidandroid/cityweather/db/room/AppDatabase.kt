package by.paranoidandroid.cityweather.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import by.paranoidandroid.cityweather.db.room.dao.CityDao
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast

@Database(entities = [RoomForecast::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "cities.db"
    }

    abstract fun cityDao(): CityDao
}