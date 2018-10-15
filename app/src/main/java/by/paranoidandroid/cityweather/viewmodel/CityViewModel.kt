package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import by.paranoidandroid.cityweather.AndroidApplication
import by.paranoidandroid.cityweather.Logger.TAG
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import by.paranoidandroid.cityweather.network.entity.WebForecast
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider

class CityViewModel @Inject constructor(val cityRepository: CityRepository) : ViewModel() {
    private var forecast: LiveData<WebForecast>? = null

    fun init(id: Int) {
        Log.d(TAG, "CityViewModel init")
        if (this.forecast != null) {
            // ViewModel is created on a per-Fragment basis,
            // so the cityId doesn't change.
            return
        }
        forecast = cityRepository.getForecast(id)
    }

    fun getForecast() = forecast
}

class CityViewModelFactory @Inject constructor(val cityRepository: CityRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  CityViewModel(cityRepository) as T
    }
}
