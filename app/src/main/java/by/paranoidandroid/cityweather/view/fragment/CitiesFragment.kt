package by.paranoidandroid.cityweather.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import by.paranoidandroid.cityweather.AndroidApplication
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.Utils.LOG_TAG
import by.paranoidandroid.cityweather.view.LoadingView
import by.paranoidandroid.cityweather.view.adapter.CityAdapter
import by.paranoidandroid.cityweather.view.bindView
import by.paranoidandroid.cityweather.viewmodel.CitiesViewModel
import by.paranoidandroid.cityweather.viewmodel.CitiesViewModelFactory
import javax.inject.Inject

class CitiesFragment: Fragment(), LoadingView {
    private val recyclerViewCities: RecyclerView by bindView(R.id.recycler_view_cities)
    private val progressBar: ContentLoadingProgressBar by bindView(R.id.progress_bar)
    private val tvError: TextView by bindView(R.id.tv_error)

    @Inject
    lateinit var viewModelFactory: CitiesViewModelFactory

    lateinit var viewModel: CitiesViewModel
    private var cityAdapter: CityAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidApplication.injector.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                                      .get(CitiesViewModel::class.java)
        cityAdapter = CityAdapter(context)

        viewModel.getForecasts().observe(this, Observer<ForecastList?> { cityList ->
            if (cityList != null) {
                cityAdapter?.updateItems(cityList)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cities, container, false)
        recyclerViewCities.adapter = cityAdapter
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