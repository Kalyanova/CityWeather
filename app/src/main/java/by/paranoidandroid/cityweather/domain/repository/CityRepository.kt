package by.paranoidandroid.cityweather.domain.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.Utils.TAG
import by.paranoidandroid.cityweather.Utils.UNCHECKED_CAST
import by.paranoidandroid.cityweather.db.room.repository.DBRepository
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.network.entity.WebForecast
import by.paranoidandroid.cityweather.network.repository.WebRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CityRepository @Inject constructor(val webService: WebRepository, val dbRepository: DBRepository) {
    var disposable: Disposable? = null

    @Suppress(UNCHECKED_CAST)
    fun getForecast(id: Int): LiveData<Forecast<Main, Coord>> {
        val data = MutableLiveData<Forecast<Main, Coord>>()
        disposable = webService.getCityForecast(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { forecast ->
                            Log.d(TAG, "onSuccess ${forecast.name}")
                            data.postValue(forecast as Forecast<Main, Coord>)
                        },
                        { throwable ->
                            Log.e(TAG, "Network error: ${throwable}")
                            dbRepository.getCityForecast(id)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(
                                            { dbforecast ->
                                                Log.d(TAG, "onSuccess ${dbforecast.name}")
                                                data.postValue(dbforecast as Forecast<Main, Coord>)
                                            },
                                            { dbthrowable ->
                                                Log.e(TAG, "Database error: ${dbthrowable}")

                                            })
                        }
                )
        return data
    }

    fun getForecastsFromNetwork(ids: String): LiveData<ForecastList> {
        val data = MutableLiveData<ForecastList>()
        disposable = webService.getForecasts(ids)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { forecastList ->
                            Log.d(TAG, "onSuccess, count: ${forecastList.cnt}")
                            data.postValue(forecastList.list as ForecastList)
                        },
                        { throwable ->
                            Log.e(TAG, "Network error: ${throwable}")
                        }
                )
        return data
    }

    @Suppress(UNCHECKED_CAST)
    fun getForecastsFromDB(): LiveData<ForecastList> {
        val data = MutableLiveData<ForecastList>()
        disposable = dbRepository.getForecasts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { forecasts ->
                            Log.d(TAG, "onSuccess, count: ${forecasts.size}")
                            data.postValue(forecasts as ForecastList)
                        },
                        { throwable ->
                            Log.e(TAG, "Database error: ${throwable}")
                        }
                )
        return data
    }

    fun dispose() = disposable?.dispose()
}