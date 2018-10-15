package by.paranoidandroid.cityweather.domain.repository

import android.arch.lifecycle.LiveData
import javax.inject.Singleton
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import by.paranoidandroid.cityweather.Logger.TAG
import by.paranoidandroid.cityweather.db.room.dao.CityDao
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.network.entity.WebForecast
import by.paranoidandroid.cityweather.network.repository.WebRepository
import dagger.Module
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@Singleton
@Module
class CityRepository {
    private lateinit var webService: WebRepository
    private lateinit var cityDao: CityDao

    @Inject
    fun CityRepository(webservice: WebRepository, cityDao: CityDao) {
        this.webService = webservice
        this.cityDao = cityDao
    }

    fun getForecast(id: Int) {//: LiveData<Forecast<Main>>? {
        val data = MutableLiveData<WebForecast>()
        webService.getCityForecast(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<Forecast<Main>> {
                    override fun onComplete() {
                        //data.postValue()
                        Log.d(TAG, "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "onSubscribe")
                    }

                    override fun onNext(t: Forecast<Main>) {
                        Log.d(TAG, "onNext ${t.name}")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError ", e)
                    }
                })


        //return data
    }

    /*fun getForecast(id: Int): LiveData<WebForecast>? {
        val data = MutableLiveData<WebForecast>()
        webService.getCityForecast(id).enqueue(object : Callback<WebForecast> {
            override fun onResponse(call: Call<WebForecast>, response: Response<WebForecast>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<WebForecast>, t: Throwable) {
                Log.e(TAG, "Exception: ", t)
            }
        })
        return data
    }*/
}