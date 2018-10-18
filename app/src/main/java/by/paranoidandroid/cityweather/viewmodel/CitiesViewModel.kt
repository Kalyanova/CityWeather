package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import javax.inject.Inject

class CitiesViewModel @Inject constructor(val cityRepository: CityRepository) : ViewModel() { //(val view: LoadingView): ViewModel() {
    private var forecasts: LiveData<ForecastList>? = null

    init {
        forecasts = cityRepository.getForecastsFromDB()
    }

    fun getForecasts(): LiveData<ForecastList>? {
        val forecastsList = forecasts?.value
        if (forecastsList != null) {
            val ids: IntArray = forecastsList.map { it.id }.toIntArray()
            forecasts = cityRepository.getForecastsFromNetwork(ids.joinToString(separator = ","), forecasts?.value)
        }
        return forecasts
    }

}

class CitiesViewModelFactory @Inject constructor(val cityRepository: CityRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CitiesViewModel(cityRepository) as T
    }
}