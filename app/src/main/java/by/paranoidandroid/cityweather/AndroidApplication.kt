package by.paranoidandroid.cityweather

import android.app.Application
import android.util.Log
import by.paranoidandroid.cityweather.Utils.LOG_TAG
import by.paranoidandroid.cityweather.db.parseCities
import by.paranoidandroid.cityweather.db.room.entity.RoomForecast
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import by.paranoidandroid.cityweather.injection.*
import javax.inject.Inject

class AndroidApplication : Application() {
    @Inject
    lateinit var cityRepository: CityRepository

    override fun onCreate() {
        super.onCreate()
        instance = this
        injector = buildComponent()
        injector.inject(this)
        checkFirstLaunchAndFillDB()
    }

    /**
     * Check whether it is the first app launch
     * and if yes, load data to database from json file.
     */
    private fun writeDB() {
        Log.d(LOG_TAG, "writeDB")
        val cities: Array<RoomForecast> = parseCities(this)
        cityRepository.writeDataToDB(cities)
    }

    /**
     * Checks whether this is the first launch, and if it's true,
     * parses json file from assets and write data to database.
     */
    private fun checkFirstLaunchAndFillDB() {
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

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .roomModule(RoomModule(this))
                .networkModule(NetworkModule())
                .build()
    }

    companion object {
        const val PREFS_FILENAME = "APP_PREFS"
        const val FIRST_LAUNCH = "FIRST_LAUNCH"

        lateinit var instance: AndroidApplication
            private set

        lateinit var injector: AppComponent
    }
}