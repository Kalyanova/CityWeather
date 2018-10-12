package by.paranoidandroid.cityweather.db

import android.content.Context
import android.util.Log
import by.paranoidandroid.cityweather.db.Logger.TAG
import by.paranoidandroid.cityweather.db.room.entity.City
import by.paranoidandroid.cityweather.db.room.entity.CityForecastRoom
import by.paranoidandroid.cityweather.domain.entity.CityForecast
import by.paranoidandroid.cityweather.domain.entity.CityList
import com.google.gson.Gson

object Logger {
    val TAG = "JsonParser"
}

fun parseFile(ctx: Context, filename: String): String {
    try {
        val inputStream = ctx.assets.open(filename)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer)
    } catch (e: Exception) {
        Log.e(TAG, "Exception: ", e)
        return ""
    }
}

fun parseCities(ctx: Context): Array<CityForecastRoom> { //Array<City> {
    val citiesStr = parseFile(ctx, "cities_with_temp.json") //val citiesStr = parseFile(ctx, "cities.json")
    //val cities: Array<City> = Gson().fromJson<Array<City>>(citiesStr, Array<City>::class.java)
    val citiesJson: CityList = Gson().fromJson<CityList>(citiesStr, CityList::class.java)
    var cities: Array<CityForecastRoom>
    //var cities = citiesJson.list
    citiesJson.list.forEach {
        Log.d(TAG, "$it")
    }
    return cities
}