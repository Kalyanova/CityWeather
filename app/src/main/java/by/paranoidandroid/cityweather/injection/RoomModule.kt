package by.paranoidandroid.cityweather.injection

import android.arch.persistence.room.Room
import android.content.Context
import by.paranoidandroid.cityweather.db.room.AppDatabase
import by.paranoidandroid.cityweather.db.room.dao.CityDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppDatabase() = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideCityDao(database: AppDatabase): CityDao {
        return database.cityDao()
    }
}