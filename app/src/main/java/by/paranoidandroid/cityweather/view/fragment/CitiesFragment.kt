package by.paranoidandroid.cityweather.view.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.paranoidandroid.cityweather.Logger.TAG
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.view.LoadingView
import by.paranoidandroid.cityweather.view.adapter.CityAdapter
import by.paranoidandroid.cityweather.viewmodel.CitiesViewModel
import kotlinx.android.synthetic.main.fragment_cities.*
import kotlinx.android.synthetic.main.fragment_cities.view.*

class CitiesFragment: Fragment(), LoadingView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cities, container, false)

        val cityAdapter = CityAdapter(context)
        view.recyclerViewCities.adapter = cityAdapter
        view.recyclerViewCities.layoutManager = LinearLayoutManager(context)

        val cityVM = CitiesViewModel(this)
        /*cityVM.cities.observe(this, object : Observer<List<City>> {
            override fun onChanged(cityList: List<City>?) {
                Log.d(TAG, "onChanged in CityFragment")
                if (cityList != null) {
                    Log.d(TAG, "onChanged and cityList != null")
                    cityAdapter.updateItems(cityList)
                }
            }
        })*/
        /*cityVM.cities.observe(this, object : Observer<List<Forecast>> {
            override fun onChanged(cityList: List<Forecast>?) {
                Log.d(TAG, "onChanged in CityFragment")
                if (cityList != null) {
                    Log.d(TAG, "onChanged and cityList != null")
                    cityAdapter.updateItems(cityList)
                }
            }
        })*/

        cityVM.cities.observe(this, object : Observer<List<Forecast<Main>>> {
            override fun onChanged(cityList: List<Forecast<Main>>?) {
                Log.d(TAG, "onChanged in CityFragment")
                if (cityList != null) {
                    Log.d(TAG, "onChanged and cityList != null")
                    cityAdapter.updateItems(cityList)
                }
            }
        })

        cityVM.getCitiesFromDB()

        /*cityVM.forecast.observe(this, object : Observer<RoomForecast> {
            override fun onChanged(forecast: RoomForecast?) {
                view.tvTemp.text = forecast?.main?.temp
            }
        })
        cityVM.updateForecast("London")*/

        return view
    }

    override fun onStartLoading() {
        Log.d(TAG, "onStartLoading")
        progressBar.show()
        progressBar.visibility = View.VISIBLE
    }

    override fun onStopLoading() {
        Log.d(TAG, "onStopLoading")
        progressBar.post { progressBar.hide() }
        progressBar.post { progressBar.visibility = View.GONE }
    }

    override fun onLoadingError(errorMsg: String) {
        Log.d(TAG, "onLoadingError")
        tvError.post {
            tvError.text = errorMsg
            tvError.visibility = View.VISIBLE
        }
    }

    override fun onLoadingSuccess() {
        Log.d(TAG, "onLoadingSuccess")
        tvError.post { tvError.visibility = View.GONE }
    }
}