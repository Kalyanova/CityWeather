package by.paranoidandroid.cityweather.db

import android.content.Context
import android.util.Log
import by.paranoidandroid.cityweather.Utils.LOG_TAG
import by.paranoidandroid.cityweather.db.room.entity.RoomCityList
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast
import com.google.gson.Gson

fun parseFile(ctx: Context, filename: String): String {
    var result = ""
    try {
        val inputStream = ctx.assets.open(filename)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        result = String(buffer)
    } catch (e: Exception) {
        Log.e(LOG_TAG, "Exception: ", e)
    }
    return result
}

fun parseCities(ctx: Context): Array<RoomForecast> {
    val citiesStr = parseFile(ctx, "cities_with_temp.json")
    val cities: RoomCityList = Gson().fromJson<RoomCityList>(citiesStr, RoomCityList::class.java)
    return cities.list
}

