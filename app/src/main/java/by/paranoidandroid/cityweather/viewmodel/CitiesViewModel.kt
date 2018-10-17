package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.*
import android.util.Log
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.Utils.LOG_TAG
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import javax.inject.Inject

class CitiesViewModel @Inject constructor(val cityRepository: CityRepository) : ViewModel() {
    private var cities: MutableLiveData<ForecastList> = cityRepository.getForecastsFromDB()

    var forecasts: LiveData<ForecastList> = Transformations.switchMap(cities) { citiesList: ForecastList ->
        getNewForecasts(citiesList, cities)
    }

    /*fun getNewForecasts(): LiveData<ForecastList> {
        Log.d(LOG_TAG, "getForecasts()")
        val forecastsList = forecasts.value
        if (forecastsList != null) {
            Log.d(LOG_TAG, "forecastsList != null")
            val ids: IntArray = forecastsList.map { it.id }.toIntArray()
            cityRepository.getForecastsFromNetwork(ids.joinToString(separator = ","), forecasts)
        }
        return forecasts
    }*/

    fun getNewForecasts(forecastsList: ForecastList, forecasts: MutableLiveData<ForecastList>): MutableLiveData<ForecastList> {
        Log.d(LOG_TAG, "getNewForecasts()")
        val ids: IntArray = forecastsList.map { it.id }.toIntArray()
        return cityRepository.getForecastsFromNetwork(ids.joinToString(separator = ","), forecasts.value!!)
    }
}

class CitiesViewModelFactory @Inject constructor(val cityRepository: CityRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CitiesViewModel(cityRepository) as T
    }
}