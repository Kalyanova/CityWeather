package by.paranoidandroid.cityweather.domain.entity

abstract class Forecast<M : Main, C: Coord>() {
    abstract var id: Int
    abstract var name: String
    abstract var description: String?
    abstract var coord: C?
    abstract var main: M?
    abstract var url: String?
}