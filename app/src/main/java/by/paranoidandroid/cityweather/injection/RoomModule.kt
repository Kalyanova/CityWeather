package by.paranoidandroid.cityweather.injection

import by.paranoidandroid.cityweather.AndroidApplication
import by.paranoidandroid.cityweather.db.room.AppDatabase
import by.paranoidandroid.cityweather.db.room.dao.CityDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: AndroidApplication) {
    private val database: AppDatabase

    init {
        // TODO: null-safety check
        database = application.database!!
    }

    @Provides
    @Singleton
    fun provideCityDao(): CityDao {
        return database.cityDao()
    }
}