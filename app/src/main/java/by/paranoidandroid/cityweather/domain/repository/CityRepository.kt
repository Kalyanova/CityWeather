package by.paranoidandroid.cityweather.domain.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import by.paranoidandroid.cityweather.Logger.TAG
import by.paranoidandroid.cityweather.network.entity.WebForecast
import by.paranoidandroid.cityweather.network.repository.WebRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CityRepository @Inject constructor(val webservice: WebRepository) {
    //private lateinit var cityDao: CityDao

    fun getForecast(id: Int): LiveData<WebForecast>? {
        val data = MutableLiveData<WebForecast>()
        webservice.getCityForecast(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { forecast ->
                            Log.d(TAG, "onSuccess, city ${forecast.name}, temp ${forecast.main?.temp}")
                            data.postValue(forecast)
                        },
                        { throwable ->
                            Log.d(TAG, "onError ${throwable}")
                        }
                )
        return data
    }
}