package by.paranoidandroid.cityweather.domain.entity

/*class CityModel(var id: Int,
                var name: String,
                var country: String,
                var temperature: String?,
                var url: String?)*/

data class CityList(
        val cnt: Int,
        val list: Array<CityForecast>
)

open class CityForecast(
        var id: Int,
        var name: String,
        var main: Main? = null,
        var url: String?
)

data class Main(
        val temp: String,
        val minTemp: String,
        val maxTemp: String
)