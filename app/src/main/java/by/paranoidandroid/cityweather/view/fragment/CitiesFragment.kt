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
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.Utils
import by.paranoidandroid.cityweather.Utils.LOG_TAG
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

    lateinit var viewModel: CitiesViewModel
    var cityAdapter: CityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidApplication.injector.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CitiesViewModel::class.java)
        cityAdapter = CityAdapter(context)

        viewModel.forecasts.observe(this, Observer<ForecastList?> { cityList ->
            Log.d(LOG_TAG, "forecasts: onChanged in CitiesFragment")
            if (cityList != null) {
                Log.d(LOG_TAG, "forecasts: onChanged and cityList != null")
                cityList.forEach {
                    Log.d(LOG_TAG, "forecasts: *** ${it.name} and ${Utils.formatDegrees(it.main?.temp)}")
                }

                cityAdapter?.let { Log.d(LOG_TAG, "forecasts: cityAdapter != null") }
                cityAdapter?.updateItems(cityList)
            }
        })
        //viewModel.getNewForecasts()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cities, container, false)
        view.recyclerViewCities.layoutManager = LinearLayoutManager(context)
        view.recyclerViewCities.adapter = cityAdapter
        Log.d(LOG_TAG, "onCreateView")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onStartLoading() {
        Log.d(LOG_TAG, "onStartLoading")
        progressBar.show()
        progressBar.visibility = View.VISIBLE
    }

    override fun onStopLoading() {
        Log.d(LOG_TAG, "onStopLoading")
        progressBar.post { progressBar.hide() }
        progressBar.post { progressBar.visibility = View.GONE }
    }

    override fun onLoadingError(errorMsg: String) {
        Log.d(LOG_TAG, "onLoadingError")
        tvError.post {
            tvError.text = errorMsg
            tvError.visibility = View.VISIBLE
        }
    }

    override fun onLoadingSuccess() {
        Log.d(LOG_TAG, "onLoadingSuccess")
        tvError.post { tvError.visibility = View.GONE }
    }
}