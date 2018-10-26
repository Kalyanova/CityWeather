package by.paranoidandroid.cityweather.domain.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.Utils.LOG_TAG
import by.paranoidandroid.cityweather.db.room.entity.RoomCoord
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast
import by.paranoidandroid.cityweather.db.room.entity.RoomMain
import by.paranoidandroid.cityweather.db.room.repository.DBRepository
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.network.repository.WebRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CityRepository @Inject constructor(val webService: WebRepository,
                                         val dbRepository: DBRepository,
                                         val context: Context) {
    var disposable: Disposable? = null
    var dbDisposable: Disposable? = null

    @Suppress("UNCHECKED_CAST")
    fun getForecast(id: Int, url: String?): LiveData<Forecast<Main, Coord>> {
        val data = MutableLiveData<Forecast<Main, Coord>>()
        disposable = webService.getCityForecast(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { forecast ->
                            Log.d(LOG_TAG, "onSuccess ${forecast.name}")
                            forecast.url = url
                            data.postValue(forecast as Forecast<Main, Coord>)
                        },
                        { throwable ->
                            Log.e(LOG_TAG, "Network error: ${throwable}")
                            dbRepository.getCityForecast(id)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(
                                            { dbforecast ->
                                                Log.d(LOG_TAG, "onSuccess ${dbforecast.name}")
                                                data.postValue(dbforecast as Forecast<Main, Coord>)
                                            },
                                            { dbthrowable ->
                                                Log.e(LOG_TAG, "DB error: ${dbthrowable}")

                                            })
                        }
                )
        return data
    }

    /**
     * @param ids: identifiers of cities
     */
    @Suppress("UNCHECKED_CAST")
    fun getForecastsFromNetwork(ids: String, oldData: ForecastList): MutableLiveData<ForecastList> {
        Log.d(LOG_TAG, "getForecastsFromNetwork")
        var data = MutableLiveData<ForecastList>()

        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            disposable = webService.getForecasts(ids)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            { forecastList ->
                                Log.d(LOG_TAG, "Success")
                                val result = forecastList.list.asList() as ForecastList

                                result.forEach {
                                    it.url =
                                        oldData.firstOrNull { item -> item.id == it.id }?.url
                                }

                                data.postValue(result)

                                val roomForecasts = Array(result.size, init = { index ->
                                    val item = result[index]
                                    val coord = RoomCoord(item.coord?.lon, item.coord?.lat)
                                    val main = RoomMain(item.main?.temp,
                                                        item.main?.minTemp,
                                                        item.main?.maxTemp)
                                    // TODO: don't update url
                                    // TODO: create another Room table with static data for city
                                    RoomForecast(item.id, item.name, coord, main, item.url)
                                })
                                writeDataToDB(roomForecasts, false)
                            },
                            { throwable ->
                                Log.e(LOG_TAG, "Network error: ${throwable}")
                                data = getForecastsFromDB()
                            }
                    )
        } else {
            Log.e(LOG_TAG, "No connection available")
            data = getForecastsFromDB()
        }
        return data
    }

    @Suppress("UNCHECKED_CAST")
    fun getForecastsFromDB(): MutableLiveData<ForecastList> {
        Log.d(LOG_TAG, "getForecastsFromDB")
        val data = MutableLiveData<ForecastList>()
        disposable = dbRepository.getForecasts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { forecasts ->
                            Log.d(LOG_TAG, "onSuccess, count: ${forecasts.size}")
                            data.postValue(forecasts as ForecastList)
                        },
                        { throwable ->
                            Log.e(LOG_TAG, "Database error: ${throwable}")
                        }
                )
        return data
    }

    fun dispose() {
        disposable?.dispose()
        dbDisposable?.dispose()
    }

    /**
     * Updating database in background
     */
    fun writeDataToDB(roomForecasts: Array<RoomForecast>, withUrls: Boolean = true) {
        // TODO: it's necessary to do all rx flows disposable and to dispose it
        dbDisposable = Completable
                .fromAction {
                    Log.d(LOG_TAG,
                            "DB updating... ${if (withUrls) "with urls" else "without urls"}")
                    if (withUrls) {
                        dbRepository.update(*roomForecasts)
                        roomForecasts.forEach {
                            Log.d(LOG_TAG, "writing url ${it.url}")
                        }
                    } else {
                        roomForecasts.forEach {
                            dbRepository.update(it)
                        }
                    }
                }
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableCompletableObserver() {
                    public override fun onStart() {
                        Log.d(LOG_TAG, "DB update started")
                    }

                    override fun onError(ex: Throwable) {
                        Log.e(LOG_TAG, "DB update error!", ex)
                    }

                    override fun onComplete() {
                        Log.d(LOG_TAG, "DB update done!")
                    }
                })
    }
}