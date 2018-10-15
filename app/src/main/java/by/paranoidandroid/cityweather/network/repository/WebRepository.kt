package by.paranoidandroid.cityweather.network.repository

import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.network.Api
import by.paranoidandroid.cityweather.network.entity.WebCityList
import by.paranoidandroid.cityweather.network.entity.WebForecast
import by.paranoidandroid.cityweather.network.utils.await
import dagger.Module
import io.reactivex.Observable
import retrofit2.Call
import javax.inject.Inject

@Module
object WebRepository {

    @Inject
    lateinit var service: Api.Service

    fun getCityForecast(id: Int): Observable<Forecast<Main>> = service.getFirecast(id)


    /*suspend fun getCityForecast(city: String): WebForecast
            = Api.service.getFirecast(city).await()*/

    //fun getCityForecast(id: Int): Call<WebForecast> = Api.service.getFirecast(id)
    //fun getCityForecast(id: Int): Observable<Forecast<Main>> = Api.service.getFirecast(id)

    /*suspend fun getFirecasts(ids: IntArray): WebCityList {
        val idsStr = ids.joinToString(separator = ",")
        return Api.service.getFirecasts(idsStr).await()
    }*/
}