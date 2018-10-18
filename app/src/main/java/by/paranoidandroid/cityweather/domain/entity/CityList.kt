package by.paranoidandroid.cityweather.domain.entity

abstract class CityList<T : Forecast<*, *>> {
    abstract var cnt: Int
    abstract var list: Array<T>
}