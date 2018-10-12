package by.paranoidandroid.cityweather

import android.app.Application
import android.util.Log
import by.paranoidandroid.cityweather.Logger.TAG
import by.paranoidandroid.cityweather.db.parseCities
import by.paranoidandroid.cityweather.db.room.AppDatabase
import by.paranoidandroid.cityweather.db.room.entity.City
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

const val PREFS_FILENAME = "APP_PREFS"
const val FIRST_LAUNCH = "FIRST_LAUNCH"

class AndroidApplication : Application() {
    companion object {
        lateinit var instance: AndroidApplication
            private set
    }

    var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = AppDatabase.getAppDatabase(this)
        checkFirstLaunch()
    }

    /**
     * Check whether it is the first app launch
     * and if yes, load data to database from json file.
     */
    private fun writeDB() {
        Log.d(TAG, "writeDB")
        val cities: Array<City> = parseCities(this)
        cities.forEach {
            Log.d(TAG, "** ${it.name}")
        }
        CoroutineScope(Dispatchers.IO).launch {
            database?.cityDao()?.insertAll(*cities)
        }
    }

    private fun checkFirstLaunch() {
        val prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        val firstLaunch = prefs.getBoolean(FIRST_LAUNCH, true)
        if (firstLaunch) {
            Log.d(TAG, "First launch")
            writeDB()
            prefs.edit()
                    .putBoolean(FIRST_LAUNCH, false)
                    .apply()
        }
        Log.d(TAG, "checkFirstLaunch finish")
    }
}