package by.paranoidandroid.cityweather.view.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.viewmodel.CityViewModel

class CityFragment: Fragment() {
    private var viewModel: CityViewModel? = null

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = arguments?.getInt(UID_KEY)
        // TODO: try with: viewModel = CityViewModel()
        viewModel = ViewModelProviders.of(this).get(CityViewModel::class.java)
        id?.let { viewModel?.init(id) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_city, container, false)
        val tvWeather = view.findViewById(R.id.tv_weather) as TextView
        tvWeather.text = viewModel?.getForecast()?.value?.main?.temp
        return view
    }
}