package by.paranoidandroid.cityweather.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionInflater
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import by.paranoidandroid.cityweather.AndroidApplication
import by.paranoidandroid.cityweather.ForecastList
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.Utils.LOG_TAG
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.view.LoadingView
import by.paranoidandroid.cityweather.view.activity.MainActivity
import by.paranoidandroid.cityweather.view.activity.MainActivity.Companion.TAG_TAB_CITIES
import by.paranoidandroid.cityweather.view.adapter.CityAdapter
import by.paranoidandroid.cityweather.view.bindView
import by.paranoidandroid.cityweather.view.transition.ImageTransition
import by.paranoidandroid.cityweather.viewmodel.CitiesViewModel
import by.paranoidandroid.cityweather.viewmodel.CitiesViewModelFactory
import kotlinx.android.synthetic.main.city_item.*
import javax.inject.Inject

class CitiesFragment: Fragment(), LoadingView, CityAdapter.OnItemClickListener {
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
        cityAdapter = CityAdapter(context, this)

        viewModel.getForecasts().observe(this, Observer<ForecastList?> { cityList ->
            if (cityList != null) {
                cityAdapter?.updateItems(cityList)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewCities.adapter = cityAdapter
    }

    override fun onClick(position: Int,  city: Forecast<Main, Coord>, vararg view: View) {
        val activity = context as? MainActivity
        val fm = activity?.supportFragmentManager
        val cityFragment = CityFragment.newInstance(city.id)

        //cityFragment.postponeEnterTransition()
        //postponeEnterTransition()

        val imageTransition = TransitionInflater.from(context).inflateTransition(R.transition.animation)

        cityFragment.sharedElementEnterTransition =  imageTransition //ImageTransition()
        cityFragment.sharedElementReturnTransition = imageTransition //ImageTransition()
        /*cityFragment.enterTransition = Fade()
        cityFragment.returnTransition = Fade()*/

        fm?.beginTransaction()
                ?.addSharedElement(view[0], ViewCompat.getTransitionName(view[0]))//context?.getString(R.string.image_transition))
                ?.addSharedElement(view[1], ViewCompat.getTransitionName(view[1]))
                ?.replace(R.id.main_container, cityFragment, TAG_TAB_CITIES)
                ?.addToBackStack(null)
                ?.commitAllowingStateLoss()
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