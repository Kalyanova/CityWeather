package by.paranoidandroid.cityweather.domain.repository

import android.arch.lifecycle.LiveData
import by.paranoidandroid.cityweather.db.room.entity.City
import javax.inject.Singleton
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import by.paranoidandroid.cityweather.Logger.TAG
import by.paranoidandroid.cityweather.network.entity.Forecast
import by.paranoidandroid.cityweather.network.repository.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@Singleton
class CityRepository {
    private lateinit var webservice: RetrofitRepository

    @Inject
    fun UserRepository(webservice: RetrofitRepository) {
        this.webservice = webservice
    }

    fun getForecast(id: Int): LiveData<Forecast>? {
        val data = MutableLiveData<Forecast>()
        webservice.getCityForecast(id).enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.e(TAG, "Exception: ", t)
            }
        })
        return data
    }


}