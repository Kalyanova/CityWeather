package by.paranoidandroid.cityweather.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.paranoidandroid.cityweather.AndroidApplication
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.Utils.TAG
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.view.LoadingView
import by.paranoidandroid.cityweather.view.adapter.CityAdapter
import by.paranoidandroid.cityweather.viewmodel.CitiesViewModel
import by.paranoidandroid.cityweather.viewmodel.CitiesViewModelFactory
import kotlinx.android.synthetic.main.fragment_cities.*
import kotlinx.android.synthetic.main.fragment_cities.view.*
import javax.inject.Inject

class CitiesFragment: Fragment(), LoadingView {
    @Inject
    lateinit var viewModelFactory: CitiesViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cities, container, false)

        val cityAdapter = CityAdapter(context)
        view.recyclerViewCities.adapter = cityAdapter
        view.recyclerViewCities.layoutManager = LinearLayoutManager(context)

        AndroidApplication.injector.inject(this)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(CitiesViewModel::class.java)
        viewModel.getForecasts()?.observe(this, object : Observer<List<Forecast<Main, Coord>>> {
            override fun onChanged(cityList: List<Forecast<Main, Coord>>?) {
                Log.d(TAG, "onChanged in CitiesFragment")
                if (cityList != null) {
                    Log.d(TAG, "onChanged and cityList != null")
                    cityAdapter.updateItems(cityList)
                }
            }
        })
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