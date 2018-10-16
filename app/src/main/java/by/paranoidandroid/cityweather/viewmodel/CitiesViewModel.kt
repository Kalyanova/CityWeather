package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.Utils.TAG
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import javax.inject.Inject

class CitiesViewModel @Inject constructor(val cityRepository: CityRepository) : ViewModel() { //(val view: LoadingView): ViewModel() {
    private var forecasts: LiveData<ForecastList>? = null

    init {
        Log.d(TAG, "Reading cities from DB")
        forecasts = cityRepository.getForecastsFromDB()
    }

    fun getForecasts(): LiveData<ForecastList>? {
        val forecastsList = forecasts?.value
        if (forecastsList != null) {
            val ids: IntArray = forecastsList.map { it.id }.toIntArray()
            forecasts = cityRepository.getForecastsFromNetwork(ids.joinToString(separator = ","))
        }
        return forecasts
    }

}

class CitiesViewModelFactory @Inject constructor(val cityRepository: CityRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CitiesViewModel(cityRepository) as T
    }
}