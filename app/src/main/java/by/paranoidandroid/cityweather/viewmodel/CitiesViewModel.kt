package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import by.paranoidandroid.cityweather.AndroidApplication
import by.paranoidandroid.cityweather.Logger.TAG
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.network.entity.WebCityList
import by.paranoidandroid.cityweather.network.entity.WebForecast
import by.paranoidandroid.cityweather.network.repository.WebRepository
import by.paranoidandroid.cityweather.view.LoadingView
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

const val DEGREE_END = " °C"
const val DOUBLE_FORMAT = "%.2f"
const val DEGREE_DIFF = 273.15

class CitiesViewModel(val view: LoadingView): ViewModel() {
    val roomDatabase = AndroidApplication.instance.database
    val forecast = MutableLiveData<Forecast<Main>>()
    var cities = MutableLiveData<List<Forecast<Main>>>()


    // region RoomForecast
    fun updateForecast(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // Updating MutableLiveData value from a background thread
            //forecast.postValue(getForecastValue(city))
        }
    }

    /*fun updateForecasts(input: List<RoomForecast>) {//List<City>) {
        view.onStartLoading()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(Logger.TAG, "Getting cities from network")
            val ids = input.map { it.id }.toIntArray()
            val cityList: CityList<Forecast<Main>>
            try {
                cityList = getForecastsValue(ids)
                val forecasts: Array<Forecast<Main>> = cityList.list
                // TODO: come up with better way to transfer data
                forecasts.forEach {
                    val cityId = it.id
                    val temp = it.main?.temp
                    temp?.let{
                        input.find { city -> city.id == cityId }?.main?.temp =
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
    }*/

    /**
     * @throws IllegalStateException
     */
    /*private suspend fun getForecastValue(city: String): WebForecast //Forecast<Main>
            = WebRepository.getCityForecast(city)

    private suspend fun getForecastsValue(ids: IntArray): WebCityList  //CityList<Forecast<Main>>
            = WebRepository.getFirecasts(ids)*/

    //endregion

    // region Cities
    /**
     * Gets cities forecasts from database.
     * This method contains unchecked cast because var/val class parameters are invariants
     * and because of that it's impossible to make Main and Forecast covariant types.
     */
    @Suppress("UNCHECKED_CAST")
    fun getCitiesFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "Reading cities from DB")
            val data: List<RoomForecast>? = roomDatabase?.cityDao()?.getAll()
            if (data != null) {
                data.forEach {
                    Log.d(TAG, "** city ${it.name}")
                }
                val forecasts: List<Forecast<Main>> = data.map { it -> it as Forecast<Main> }
                // Updating MutableLiveData value from a background thread
                cities.postValue(forecasts)
            } else {
                Log.d(TAG, "cities == null")
            }
        }
    }
    //endregion
}