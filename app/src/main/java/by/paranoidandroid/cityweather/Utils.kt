package by.paranoidandroid.cityweather

import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main

typealias ForecastList = List<Forecast<Main, Coord>>

object Utils {
    const val LOG_TAG = "CityWeatherApp"
    const val DEGREE_END = " °C"
    const val DOUBLE_FORMAT = "%.2f"
    const val DEGREE_DIFF = 273.15
}

fun String.formatDegrees() =
    Utils.DOUBLE_FORMAT.format(this.toDouble() - Utils.DEGREE_DIFF) + Utils.DEGREE_END

inline fun <reified T : Any> requireType(value: Any?): T {
    return value as? T ?: throw ClassCastException(
        "Expected type is ${T::class.java.canonicalName}, but actual type is " +
                if (value != null) value::class.java.canonicalName else "null"
    )
}