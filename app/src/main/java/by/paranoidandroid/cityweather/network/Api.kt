package by.paranoidandroid.cityweather.network

import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.network.entity.WebCityList
import by.paranoidandroid.cityweather.network.entity.WebForecast
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Singleton for working with OpenWeatherMap api.
 */
@Module
object Api {
    const val BASE_URL = "https://api.openweathermap.org"
    const val APP_ID = "5aac6d22ae15714a889b0525c5de1480"

    interface Service {

        @GET("/data/2.5/weather")
        fun getFirecast(
                @Query("q") city: String,
                @Query("appid") appid: String = APP_ID
        ): Call<WebForecast>

        /*@GET("/data/2.5/weather")
        fun getFirecast(
                @Query("id") cityId: Int,
                @Query("appid") appid: String = APP_ID
        ): Call<WebForecast>*/

        @GET("/data/2.5/weather")
        fun getFirecast(
                @Query("id") cityId: Int,
                @Query("appid") appid: String = APP_ID
        ): Observable<Forecast<Main>>

        @GET("/data/2.5/group")
        fun getFirecasts(
                @Query("id", encoded = true) id: String,
                @Query("appid") appid: String = APP_ID
        ): Call<WebCityList>
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        val logInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC)

        val httpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient)
                .build()
    }

    @Provides
    fun provideService(retrofit: Retrofit): Service {
       return retrofit.create(Service::class.java)
    }

    /*private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

    val service = retrofit.create(Service::class.java)*/
}