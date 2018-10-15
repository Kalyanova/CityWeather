package by.paranoidandroid.cityweather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import by.paranoidandroid.cityweather.network.entity.Forecast
import javax.inject.Inject

class CityViewModel @Inject constructor(val cityRepository: CityRepository) : ViewModel() {
    private var forecast: LiveData<Forecast>? = null

    fun init(id: Int) {
        if (this.forecast != null) {
            // ViewModel is created on a per-Fragment basis,
            // so the cityId doesn't change.
            return;
        }
        forecast = cityRepository.getForecast(id)
    }

    fun getForecast() = forecast
}