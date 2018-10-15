package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import by.paranoidandroid.cityweather.AndroidApplication
import by.paranoidandroid.cityweather.Logger
import by.paranoidandroid.cityweather.db.Logger.TAG
import by.paranoidandroid.cityweather.db.room.entity.City
import by.paranoidandroid.cityweather.network.entity.CityList
import by.paranoidandroid.cityweather.network.entity.Forecast
import by.paranoidandroid.cityweather.network.repository.RetrofitRepository
import by.paranoidandroid.cityweather.view.LoadingView
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

const val DEGREE_END = " °C"
const val DOUBLE_FORMAT = "%.2f"
const val DEGREE_DIFF = 273.15

class CitiesViewModel(val view: LoadingView): ViewModel() {
    val roomDatabase = AndroidApplication.instance.database
    val forecast = MutableLiveData<Forecast>()
    var cities = MutableLiveData<List<City>>()


    // region Forecast
    fun updateForecast(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // Updating MutableLiveData value from a background thread
            forecast.postValue(getForecastValue(city))
        }
    }

    fun updateForecasts(input: List<City>) {
        view.onStartLoading()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(Logger.TAG, "Getting cities from network")
            val ids = input.map { it.id }.toIntArray()
            val cityList: CityList
            try {
                cityList = getForecastsValue(ids)
                val forecasts: Array<Forecast> = cityList.list
                // TODO: come up with better way to transfer data
                forecasts.forEach {
                    val cityId = it.id
                    val temp = it.main?.temp
                    temp?.let{
                        input.find{ city -> city.id == cityId }?.temperature =
                                DOUBLE_FORMAT.format(temp.toDouble() - DEGREE_DIFF) + DEGREE_END
                    }
                }
                view.onLoadingSuccess()
                // Updating MutableLiveData value from a background thread
                cities.postValue(input)
            } catch (e: Exception) {
                view.onLoadingError(e.message.toString())
            }
            view.onStopLoading()
        }
    }

    /**
     * @throws IllegalStateException
     */
    private suspend fun getForecastValue(city: String): Forecast
            = RetrofitRepository.getCityForecast(city)

    private suspend fun getForecastsValue(ids: IntArray): CityList
            = RetrofitRepository.getFirecasts(ids)

    //endregion

    // region Cities
    fun getCitiesFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "Reading cities from DB")
            // Updating MutableLiveData value from a background thread
            val data = roomDatabase?.cityDao()?.getAll()
            if (data != null) {
                data.forEach {
                    Log.d(TAG, "** city ${it.name}")
                }
                updateForecasts(data)
            } else {
                Log.d(TAG, "cities == null")
            }
            //cities.postValue(data)
        }
    }
    //endregion
}