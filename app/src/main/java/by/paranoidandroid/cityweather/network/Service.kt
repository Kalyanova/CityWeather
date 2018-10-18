package by.paranoidandroid.cityweather.network

import by.paranoidandroid.cityweather.injection.RetrofitModule.Companion.APP_ID
import by.paranoidandroid.cityweather.network.entity.WebCityList
import by.paranoidandroid.cityweather.network.entity.WebForecast
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("/data/2.5/weather")
    fun getForecast(
            @Query("q") city: String,
            @Query("appid") appId: String = APP_ID
    ): Single<WebForecast>

    @GET("/data/2.5/weather")
    fun getForecast(
            @Query("id") cityId: Int,
            @Query("appid") appId: String = APP_ID
    ): Single<WebForecast>

    @GET("/data/2.5/group")
    fun getForecasts(
            @Query("id", encoded = true) ids: String,
            @Query("appid") appId: String = APP_ID
    ): Single<WebCityList>
}