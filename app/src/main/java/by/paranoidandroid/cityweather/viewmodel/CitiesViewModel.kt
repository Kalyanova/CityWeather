package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.*
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import by.paranoidandroid.cityweather.requireType
import javax.inject.Inject

class CitiesViewModel @Inject constructor(private val cityRepository: CityRepository) :
        ViewModel() {

    var cities: MutableLiveData<ForecastList> = cityRepository.getForecastsFromDB()

    fun getForecasts(): LiveData<ForecastList> = Transformations.switchMap(cities) {
        getNewForecasts(it)
    }

    private fun getNewForecasts(forecastsList: ForecastList): MutableLiveData<ForecastList> {
        val ids: IntArray = forecastsList.map { it.id }.toIntArray()
        return cityRepository.getForecastsFromNetwork(ids.joinToString(separator = ","),
                                                      forecastsList)
    }

    fun deleteForecast(cityName: String) = cityRepository.deleteForecast(cityName)

    fun insertForecast(forecast: Forecast<Main, Coord>) = cityRepository.insertForecast(forecast)
}

class CitiesViewModelFactory @Inject constructor(private val cityRepository: CityRepository) :
        ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CitiesViewModel(cityRepository) as T
    }
}
