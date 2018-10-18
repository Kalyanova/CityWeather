package by.paranoidandroid.cityweather.db.room.entity

import by.paranoidandroid.cityweather.domain.entity.CityList
import java.util.*

data class RoomCityList(
        override var cnt: Int,
        override var list: Array<RoomForecast>
) : CityList<RoomForecast>() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoomCityList

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
