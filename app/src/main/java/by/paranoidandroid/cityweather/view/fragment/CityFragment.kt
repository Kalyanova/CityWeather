package by.paranoidandroid.cityweather.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
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
import android.view.ViewTreeObserver
import android.widget.ImageView
import by.paranoidandroid.cityweather.Utils.LOG_TAG
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class CityFragment : Fragment() {
    private val tvCityName: TextView by bindView(R.id.tv_city_name)
    private val tvWeather: TextView by bindView(R.id.tv_weather)
    private val cityImage: ImageView by bindView(R.id.iv_city_image)
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
        return inflater.inflate(R.layout.fragment_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
    }

    private fun updateUI() {
        /*cityImage.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        Log.d(LOG_TAG, "onPreDraw")
                        cityImage.viewTreeObserver.removeOnPreDrawListener(this)
                        startPostponedEnterTransition()
                        return true
                    }
                })*/
        tvCityName.text = viewModel?.getForecast()?.value?.name
        tvWeather.text = viewModel?.getForecast()?.value?.main?.temp?.formatDegrees()
        val requestOptions = RequestOptions()
                .placeholder(R.drawable.city_placeholder)
                .error(R.drawable.city_placeholder)
        if (activity != null) {
            Glide.with(requireActivity()).setDefaultRequestOptions(requestOptions)
                    .load(viewModel?.getForecast()?.value?.url)
                    .into(cityImage)
        }
    }

    override fun onPause() {
        viewModel?.dispose()
        super.onPause()
    }
}