package by.paranoidandroid.cityweather.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import by.paranoidandroid.cityweather.AndroidApplication
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.Utils.formatDegrees
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.viewmodel.CityViewModel
import by.paranoidandroid.cityweather.viewmodel.CityViewModelFactory
import javax.inject.Inject

class CityFragment: Fragment() {
    private var viewModel: CityViewModel? = null
    private var tvCityName: TextView? = null
    private var tvWeather: TextView ? = null

    @Inject
    lateinit var viewModelFactory: CityViewModelFactory

    companion object {
        private val UID_KEY = "uid"

        fun newInstance(id: Int): CityFragment {
            val args = Bundle()
            args.putSerializable(UID_KEY, id)
            val fragment = CityFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getInt(UID_KEY)
        AndroidApplication.injector.inject(this)
        viewModel = ViewModelProviders.of(
                this,
                viewModelFactory
        ).get(CityViewModel::class.java)
        id?.let { viewModel?.init(id) }
        viewModel?.getForecast()
                ?.observe(this, Observer<Forecast<Main, Coord>> { updateUI() })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_city, container, false)
        tvCityName = view.findViewById(R.id.tv_city_name) as TextView
        tvWeather = view.findViewById(R.id.tv_weather) as TextView
        updateUI()
        return view
    }

    private fun updateUI() {
        tvCityName?.text = viewModel?.getForecast()?.value?.name
        tvWeather?.text = formatDegrees(viewModel?.getForecast()?.value?.main?.temp)
    }

    override fun onPause() {
        viewModel?.dispose()
        super.onPause()
    }
}