package by.paranoidandroid.cityweather.db.room.repository

import by.paranoidandroid.cityweather.db.room.dao.CityDao
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast
import io.reactivex.Single
import javax.inject.Inject

class DBRepository @Inject constructor(private val cityDao: CityDao) {

    fun getCityForecast(id: Int): Single<RoomForecast> = cityDao.get(id)

    fun getForecasts(): Single<List<RoomForecast>> = cityDao.getAll()

    fun update(vararg forecast: RoomForecast) = cityDao.insertAll(*forecast)

    fun update(forecast: RoomForecast) =
        cityDao.updateWithoutUrl(
            forecast.id,
            forecast.coord?.lon, forecast.coord?.lat,
            forecast.main?.temp, forecast.main?.minTemp, forecast.main?.maxTemp
        )

    fun delete(cityName: String) = cityDao.delete(cityName)

    fun insert(forecast: RoomForecast) = cityDao.insert(forecast)
}