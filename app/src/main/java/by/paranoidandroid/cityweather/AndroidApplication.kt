package by.paranoidandroid.cityweather

import android.app.Application
import android.util.Log
import by.paranoidandroid.cityweather.Utils.LOG_TAG
import by.paranoidandroid.cityweather.db.parseCities
import by.paranoidandroid.cityweather.db.room.AppDatabase
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast
import by.paranoidandroid.cityweather.injection.AppComponent
import by.paranoidandroid.cityweather.injection.AppModule
import by.paranoidandroid.cityweather.injection.DaggerAppComponent
import by.paranoidandroid.cityweather.injection.RoomModule
import by.paranoidandroid.cityweather.network.Api
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

class AndroidApplication : Application() {
    companion object {
        const val PREFS_FILENAME = "APP_PREFS"
        const val FIRST_LAUNCH = "FIRST_LAUNCH"

        lateinit var instance: AndroidApplication
            private set

        lateinit var injector: AppComponent
    }

    var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = AppDatabase.getAppDatabase(this)
        checkFirstLaunch()
        injector = buildComponent()
    }

    /**
     * Check whether it is the first app launch
     * and if yes, load data to database from json file.
     */
    private fun writeDB() {
        Log.d(LOG_TAG, "writeDB")
        val cities: Array<RoomForecast> = parseCities(this)
        // TODO: Remove coroutines
        CoroutineScope(Dispatchers.IO).launch {
            database?.cityDao()?.insertAll(*cities)
        }
    }

    /**
     * Checks whether this is the first launch, and if it's true,
     * parses json file from assets and write data to database.
     */
    private fun checkFirstLaunch() {
        val prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        val firstLaunch = prefs.getBoolean(FIRST_LAUNCH, true)
        if (firstLaunch) {
            Log.d(LOG_TAG, "First launch")
            writeDB()
            prefs.edit()
                    .putBoolean(FIRST_LAUNCH, false)
                    .apply()
        }
    }

    protected fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .api(Api)
                .appModule(AppModule(this))
                .roomModule(RoomModule(this))
                .build()
    }
}