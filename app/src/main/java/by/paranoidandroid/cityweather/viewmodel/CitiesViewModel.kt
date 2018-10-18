package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.*
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import javax.inject.Inject

class CitiesViewModel @Inject constructor(private val cityRepository: CityRepository) : ViewModel() {
    private var cities: MutableLiveData<ForecastList> = cityRepository.getForecastsFromDB()

    fun getForecasts(): LiveData<ForecastList> = Transformations.switchMap(cities) { citiesList: ForecastList ->
        getNewForecasts(citiesList, cities)
    }

    private fun getNewForecasts(forecastsList: ForecastList, forecasts: MutableLiveData<ForecastList>): MutableLiveData<ForecastList> {
        val ids: IntArray = forecastsList.map { it.id }.toIntArray()
        return cityRepository.getForecastsFromNetwork(ids.joinToString(separator = ","), forecasts.value)
    }
}

class CitiesViewModelFactory @Inject constructor(val cityRepository: CityRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CitiesViewModel(cityRepository) as T
    }
}