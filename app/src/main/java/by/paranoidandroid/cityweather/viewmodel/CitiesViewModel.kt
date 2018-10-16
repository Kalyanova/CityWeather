package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import by.paranoidandroid.cityweather.Utils.TAG
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import javax.inject.Inject

class CitiesViewModel @Inject constructor(cityRepository: CityRepository) : ViewModel() { //(val view: LoadingView): ViewModel() {
    val forecast = MutableLiveData<Forecast<Main, Coord>>()
    var cities: LiveData<List<Forecast<Main, Coord>>>? = null

    init {
        Log.d(TAG, "Reading cities from DB")
        cities = cityRepository.getForecastsFromDB()
    }

}

class CitiesViewModelFactory @Inject constructor(val cityRepository: CityRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  CitiesViewModel(cityRepository) as T
    }
}