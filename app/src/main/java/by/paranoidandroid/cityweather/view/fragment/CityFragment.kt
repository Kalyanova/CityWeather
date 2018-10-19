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
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.formatDegrees
import by.paranoidandroid.cityweather.view.bindView
import by.paranoidandroid.cityweather.viewmodel.CityViewModel
import by.paranoidandroid.cityweather.viewmodel.CityViewModelFactory
import javax.inject.Inject

class CityFragment : Fragment() {
    private val tvCityName: TextView by bindView(R.id.tv_city_name)
    private val tvWeather: TextView by bindView(R.id.tv_weather)
    private var viewModel: CityViewModel? = null

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
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                                      .get(CityViewModel::class.java)
        id?.let { viewModel?.init(id) }
        viewModel?.getForecast()
                ?.observe(this, Observer<Forecast<Main, Coord>> { updateUI() })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        updateUI()
        return view
    }

    private fun updateUI() {
        tvCityName.text = viewModel?.getForecast()?.value?.name
        tvWeather.text = viewModel?.getForecast()?.value?.main?.temp?.formatDegrees()
    }

    override fun onPause() {
        viewModel?.dispose()
        super.onPause()
    }
}