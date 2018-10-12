package by.paranoidandroid.cityweather.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.db.room.AppDatabase
import by.paranoidandroid.cityweather.view.fragment.CityFragment
import by.paranoidandroid.cityweather.view.fragment.MapFragment
import by.paranoidandroid.cityweather.view.fragment.SettingsFragment

const val TAG_1 = "TAG_1"
const val TAG_2 = "TAG_2"
const val TAG_3 = "TAG_3"
const val ARGS_ACTIVE_FRAGMENT = "ARGS_ACTIVE_FRAGMENT"

class MainActivity : AppCompatActivity() {

    val fm: FragmentManager = supportFragmentManager
    var bottomNavView: BottomNavigationView? = null
    var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottom_nav_view)

        val cityFragment = CityFragment()
        val mapFragment = MapFragment()
        val settingsFragment = SettingsFragment()

        if (savedInstanceState == null) {
            activeFragment = cityFragment

            fm.beginTransaction()
                    .add(R.id.main_container, cityFragment, TAG_1)
                    .commit()

            fm.beginTransaction()
                    .add(R.id.main_container, mapFragment, TAG_2)
                    .hide(mapFragment)
                    .commit()

            fm.beginTransaction()
                    .add(R.id.main_container, settingsFragment, TAG_3)
                    .hide(settingsFragment)
                    .commit()
        } else {
            activeFragment = when (savedInstanceState.getString(ARGS_ACTIVE_FRAGMENT)) {
                TAG_1 -> CityFragment()
                TAG_2 -> MapFragment()
                TAG_3 -> SettingsFragment()
                else -> CityFragment()
            }
        }

        bottomNavView?.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_cities -> {
                        resetActiveFragment(cityFragment)
                    }
                    R.id.action_map -> {
                        resetActiveFragment(mapFragment)
                    }
                    R.id.action_settings -> {
                        resetActiveFragment(settingsFragment)
                    }
                    else -> {
                        resetActiveFragment(cityFragment)
                    }
                }
                return@setOnNavigationItemSelectedListener true
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        when (activeFragment) {
            is CityFragment -> outState.putString(ARGS_ACTIVE_FRAGMENT, TAG_1)
            is MapFragment -> outState.putString(ARGS_ACTIVE_FRAGMENT, TAG_2)
            is SettingsFragment -> outState.putString(ARGS_ACTIVE_FRAGMENT, TAG_3)
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * We have only one Activity, thus destroying this Activity imply destroying the app.
     * TODO: ask whether there is a better solution.
     */
    override fun onDestroy() {
        AppDatabase.destroyDatabase()
        super.onDestroy()
    }

    fun resetActiveFragment(newActiveFragment: Fragment) {
        fm.beginTransaction()
                .hide(activeFragment)
                .show(newActiveFragment)
                .commit()
        activeFragment = newActiveFragment
    }
}