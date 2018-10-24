package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.*
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import javax.inject.Inject

class CitiesViewModel @Inject constructor(private val cityRepository: CityRepository) :
        ViewModel() {

    private var cities: MutableLiveData<ForecastList> = cityRepository.getForecastsFromDB()

    fun getForecasts(): LiveData<ForecastList> = Transformations.switchMap(cities) {
        getNewForecasts(it)
    }

    private fun getNewForecasts(forecastsList: ForecastList): MutableLiveData<ForecastList> {
        val ids: IntArray = forecastsList.map { it.id }.toIntArray()
        return cityRepository.getForecastsFromNetwork(ids.joinToString(separator = ","),
                                                      forecastsList)
    }
}

class CitiesViewModelFactory @Inject constructor(private val cityRepository: CityRepository) :
        ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CitiesViewModel(cityRepository) as T
    }
}