package by.paranoidandroid.cityweather.view.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.db.Logger.TAG
import by.paranoidandroid.cityweather.db.room.entity.City
import by.paranoidandroid.cityweather.view.LoadingView
import by.paranoidandroid.cityweather.view.adapter.CityAdapter
import by.paranoidandroid.cityweather.viewmodel.CityViewModel
import kotlinx.android.synthetic.main.fragment_city.*
import kotlinx.android.synthetic.main.fragment_city.view.*

class CityFragment: Fragment(), LoadingView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)

        val cityAdapter = CityAdapter(context)
        view.recyclerViewCities.adapter = cityAdapter
        view.recyclerViewCities.layoutManager = LinearLayoutManager(context)

        val cityVM = CityViewModel(this)
        cityVM.cities.observe(this, object : Observer<List<City>> {
            override fun onChanged(cityList: List<City>?) {
                Log.d(TAG, "onChanged in CityFragment")
                if (cityList != null) {
                    Log.d(TAG, "onChanged and cityList != null")
                    cityAdapter.updateItems(cityList)
                }
            }
        })
        cityVM.getCitiesFromDB()

        /*cityVM.forecast.observe(this, object : Observer<Forecast> {
            override fun onChanged(forecast: Forecast?) {
                view.tvTemp.text = forecast?.main?.temp
            }
        })
        cityVM.updateForecast("London")*/
        return view
    }

    override fun onStartLoading() {
        Log.d(TAG, "onStartLoading")
        progress_bar.show()
        progress_bar.visibility = View.VISIBLE
    }

    override fun onStopLoading() {
        Log.d(TAG, "onStopLoading")
        progress_bar.post { progress_bar.hide() }
        progress_bar.post { progress_bar.visibility = View.GONE }

    }
}