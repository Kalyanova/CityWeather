package by.paranoidandroid.cityweather.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.Utils.LOG_TAG
import by.paranoidandroid.cityweather.view.bindView
import by.paranoidandroid.cityweather.view.fragment.base.CitiesFragment
import by.paranoidandroid.cityweather.view.fragment.base.MapFragment
import by.paranoidandroid.cityweather.view.fragment.base.SettingsFragment

class MainActivity : AppCompatActivity() {
    private val bottomNavView: BottomNavigationView by bindView(R.id.bottom_nav_view)
    private val fm: FragmentManager = supportFragmentManager
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityFragment = CitiesFragment()
        val mapFragment = MapFragment()
        val settingsFragment = SettingsFragment()

        if (savedInstanceState == null) {
            activeFragment = cityFragment

            fm.beginTransaction()
                    .add(R.id.main_container, cityFragment, TAG_TAB_CITIES)
                    .commit()

            fm.beginTransaction()
                    .add(R.id.main_container, mapFragment, TAG_TAB_MAP)
                    .hide(mapFragment)
                    .commit()

            fm.beginTransaction()
                    .add(R.id.main_container, settingsFragment, TAG_TAB_SETTINGS)
                    .hide(settingsFragment)
                    .commit()
        } else {
            activeFragment = when (savedInstanceState.getString(ARGS_ACTIVE_FRAGMENT)) {
                TAG_TAB_MAP -> MapFragment()
                TAG_TAB_SETTINGS -> SettingsFragment()
                else -> CitiesFragment()
            }
        }

        bottomNavView.setOnNavigationItemSelectedListener { item ->
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
            Log.d(LOG_TAG, "active fragment is ${activeFragment.toString()}")
            return@setOnNavigationItemSelectedListener true
        }

        Log.d(LOG_TAG, "active fragment is ${activeFragment.toString()}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        when (activeFragment) {
            is CitiesFragment -> outState.putString(ARGS_ACTIVE_FRAGMENT, TAG_TAB_CITIES)
            is MapFragment -> outState.putString(ARGS_ACTIVE_FRAGMENT, TAG_TAB_MAP)
            is SettingsFragment -> outState.putString(ARGS_ACTIVE_FRAGMENT, TAG_TAB_SETTINGS)
        }
        super.onSaveInstanceState(outState)
    }

    private fun resetActiveFragment(newActiveFragment: Fragment) {
        fm.beginTransaction()
                .hide(activeFragment)
                .show(newActiveFragment)
                .commit()
        activeFragment = newActiveFragment
    }

    companion object {
        const val TAG_TAB_CITIES = "TAG_TAB_CITIES"
        const val TAG_TAB_MAP = "TAG_TAB_MAP"
        const val TAG_TAB_SETTINGS = "TAG_TAB_SETTINGS"
        const val ARGS_ACTIVE_FRAGMENT = "ARGS_ACTIVE_FRAGMENT"
    }
}