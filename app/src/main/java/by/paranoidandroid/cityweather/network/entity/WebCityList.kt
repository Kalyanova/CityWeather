package by.paranoidandroid.cityweather.network.entity

import by.paranoidandroid.cityweather.domain.entity.CityList
import java.util.*

data class WebCityList(
        override var cnt: Int,
        override var list: Array<WebForecast>
) : CityList<WebForecast>() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WebCityList

        if (cnt != other.cnt) return false
        if (!Arrays.equals(list, other.list)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cnt
        result = 31 * result + Arrays.hashCode(list)
        return result
    }
}