package by.paranoidandroid.cityweather.network.repository

import by.paranoidandroid.cityweather.network.Service
import by.paranoidandroid.cityweather.network.entity.WebCityList
import by.paranoidandroid.cityweather.network.entity.WebForecast
import io.reactivex.Single
import javax.inject.Inject

class WebRepository @Inject constructor(private val service: Service) {

    fun getCityForecast(id: Int): Single<WebForecast> = service.getForecast(id)

    fun getForecasts(ids: String): Single<WebCityList> = service.getForecasts(ids)
}