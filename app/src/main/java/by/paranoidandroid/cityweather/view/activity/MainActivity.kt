package by.paranoidandroid.cityweather.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.Utils.ARGS_ACTIVE_FRAGMENT
import by.paranoidandroid.cityweather.Utils.TAG_1_TAB
import by.paranoidandroid.cityweather.Utils.TAG_2_TAB
import by.paranoidandroid.cityweather.Utils.TAG_3_TAB
import by.paranoidandroid.cityweather.view.fragment.CitiesFragment
import by.paranoidandroid.cityweather.view.fragment.MapFragment
import by.paranoidandroid.cityweather.view.fragment.SettingsFragment

class MainActivity : AppCompatActivity() {
    val fm: FragmentManager = supportFragmentManager
    var bottomNavView: BottomNavigationView? = null
    var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottom_nav_view)

        val cityFragment = CitiesFragment()
        val mapFragment = MapFragment()
        val settingsFragment = SettingsFragment()

        if (savedInstanceState == null) {
            activeFragment = cityFragment

            fm.beginTransaction()
                    .add(R.id.main_container, cityFragment, TAG_1_TAB)
                    .commit()

            fm.beginTransaction()
                    .add(R.id.main_container, mapFragment, TAG_2_TAB)
                    .hide(mapFragment)
                    .commit()

            fm.beginTransaction()
                    .add(R.id.main_container, settingsFragment, TAG_3_TAB)
                    .hide(settingsFragment)
                    .commit()
        } else {
            activeFragment = when (savedInstanceState.getString(ARGS_ACTIVE_FRAGMENT)) {
                TAG_2_TAB -> MapFragment()
                TAG_3_TAB -> SettingsFragment()
                else -> CitiesFragment()
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
            is CitiesFragment -> outState.putString(ARGS_ACTIVE_FRAGMENT, TAG_1_TAB)
            is MapFragment -> outState.putString(ARGS_ACTIVE_FRAGMENT, TAG_2_TAB)
            is SettingsFragment -> outState.putString(ARGS_ACTIVE_FRAGMENT, TAG_3_TAB)
        }
        super.onSaveInstanceState(outState)
    }

    fun resetActiveFragment(newActiveFragment: Fragment) {
        fm.beginTransaction()
                .hide(activeFragment)
                .show(newActiveFragment)
                .commit()
        activeFragment = newActiveFragment
    }
}