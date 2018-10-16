package by.paranoidandroid.cityweather.network.repository

import by.paranoidandroid.cityweather.network.Api
import by.paranoidandroid.cityweather.network.entity.WebCityList
import by.paranoidandroid.cityweather.network.entity.WebForecast
import io.reactivex.Single
import javax.inject.Inject

class WebRepository @Inject constructor(private val service: Api.Service) {

    fun getCityForecast(id: Int): Single<WebForecast> = service.getFirecast(id)

    fun getForecasts(ids: String): Single<WebCityList> = service.getFirecasts(ids)
}