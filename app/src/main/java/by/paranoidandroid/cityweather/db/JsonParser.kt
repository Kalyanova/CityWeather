package by.paranoidandroid.cityweather.db

import android.content.Context
import android.util.Log
import by.paranoidandroid.cityweather.db.Logger.TAG
import by.paranoidandroid.cityweather.db.room.entity.City
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

fun parseCities(ctx: Context): Array<City> {
    val citiesStr = parseFile(ctx, "cities.json")
    val cities: Array<City> = Gson().fromJson<Array<City>>(citiesStr, Array<City>::class.java)
    cities.forEach { Log.d(TAG, "$it") }
    return cities
}