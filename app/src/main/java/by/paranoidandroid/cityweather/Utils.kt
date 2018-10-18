package by.paranoidandroid.cityweather

import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main

typealias ForecastList =  List<Forecast<Main, Coord>>

object Utils {
    const val TAG_TAB_CITIES = "TAG_TAB_CITIES"
    const val TAG_TAB_MAP = "TAG_TAB_MAP"
    const val TAG_TAB_SETTINGS = "TAG_TAB_SETTINGS"
    const val ARGS_ACTIVE_FRAGMENT = "ARGS_ACTIVE_FRAGMENT"

    const val LOG_TAG = "CityWeatherApp"
    const val UNCHECKED_CAST = "UNCHECKED_CAST"
    const val DEGREE_END = " °C"
    const val DOUBLE_FORMAT = "%.2f"
    const val DEGREE_DIFF = 273.15

    fun formatDegrees(temp: String?): String {
        val tempValue: Double = temp?.toDouble() ?: return ""
        return DOUBLE_FORMAT.format( tempValue - DEGREE_DIFF) + DEGREE_END
    }
}