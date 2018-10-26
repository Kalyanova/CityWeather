package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import javax.inject.Inject

class CityViewModel @Inject constructor(private val cityRepository: CityRepository) :
        ViewModel() {

    private var forecast: LiveData<Forecast<Main, Coord>> ? = null

    fun init(id: Int, url: String?) {
        if (this.forecast != null) {
            return
        }
        forecast = cityRepository.getForecast(id, url)
    }

    fun getForecast() = forecast

    fun dispose() {
        cityRepository.dispose()
    }
}

class CityViewModelFactory @Inject constructor(val cityRepository: CityRepository) :
        ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  CityViewModel(cityRepository) as T
    }
}
