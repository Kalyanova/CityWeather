package by.paranoidandroid.cityweather.network.repository

import by.paranoidandroid.cityweather.network.Api
import by.paranoidandroid.cityweather.network.entity.CityList
import by.paranoidandroid.cityweather.network.entity.Forecast
import by.paranoidandroid.cityweather.network.utils.await

object RetrofitRepository {
    suspend fun getCityForecast(city: String): Forecast
            = Api.service.getFirecast(city).await()

    suspend fun getFirecasts(ids: IntArray): CityList {
        val idsStr = ids.joinToString(separator = ",")
        return Api.service.getFirecasts(idsStr).await()
    }
}