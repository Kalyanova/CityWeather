package by.paranoidandroid.cityweather.db

import android.content.Context
import android.util.Log
import by.paranoidandroid.cityweather.Logger.TAG
import by.paranoidandroid.cityweather.db.room.entity.RoomCityList
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast
import com.google.gson.Gson

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

fun parseCities(ctx: Context): Array<RoomForecast> {
    val citiesStr = parseFile(ctx, "cities_with_temp.json")
    val cities: RoomCityList = Gson().fromJson<RoomCityList>(citiesStr, RoomCityList::class.java)
    //val forecasts: Array<RoomForecast> =  cities.list //.map { it -> it as RoomForecast }.toTypedArray()
    return cities.list //forecasts
}

