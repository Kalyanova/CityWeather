package by.paranoidandroid.cityweather.domain.entity

abstract class CityList<T : Forecast<*, *>> {
    abstract var cnt: Int
    abstract var list: Array<T>
}

abstract class Forecast<M : Main, C: Coord>() {
    abstract var id: Int
    abstract var name: String
    abstract var coord: C?
    abstract var main: M?
    abstract var url: String?
}

abstract class Main {
    abstract var temp: String?
    abstract var minTemp: String?
    abstract var maxTemp: String?
}

abstract class Coord {
    abstract var lon: Double?
    abstract var lat: Double?
}