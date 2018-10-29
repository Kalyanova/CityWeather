package by.paranoidandroid.cityweather.view.fragment.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.transition.Fade
import android.support.v4.app.Fragment
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import by.paranoidandroid.cityweather.view.fragment.city.CityFragment
import by.paranoidandroid.cityweather.view.transition.ImageTransition
import by.paranoidandroid.cityweather.viewmodel.CitiesViewModel
import by.paranoidandroid.cityweather.viewmodel.CitiesViewModelFactory
import javax.inject.Inject

class CitiesFragment: Fragment(), LoadingView, CityAdapter.OnItemClickListener {
    private val progressBar: ContentLoadingProgressBar by bindView(R.id.progress_bar)
    private val tvError: TextView by bindView(R.id.tv_error)

    @Inject
    lateinit var viewModelFactory: CitiesViewModelFactory

    private lateinit var viewModel: CitiesViewModel
    private var cityAdapter: CityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        AndroidApplication.injector.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                                      .get(CitiesViewModel::class.java)
        cityAdapter = CityAdapter(context, this)

        viewModel.cities.value?.let {
            cityAdapter?.updateItems(it)
        }

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
        val recyclerViewCities = view.findViewById<RecyclerView>(R.id.recycler_view_cities)
        recyclerViewCities.adapter = cityAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_cities, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_sync ->
                Toast.makeText(context, "Sync", Toast.LENGTH_SHORT).show()
            R.id.action_add_city ->
                Toast.makeText(context, "Adding city", Toast.LENGTH_SHORT).show()
            R.id.action_show_error ->
                Toast.makeText(context, "Show error", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(position: Int,  city: Forecast<Main, Coord>, animationTarget: ImageView) {
        val activity = context as? MainActivity
        val fm = activity?.supportFragmentManager
        val cityFragment = CityFragment.newInstance(city.id, city.url)

        cityFragment.postponeEnterTransition()
        cityFragment.sharedElementEnterTransition = ImageTransition()
        cityFragment.sharedElementReturnTransition = ImageTransition()
        cityFragment.enterTransition = Fade()
        cityFragment.returnTransition = Fade()

        fm?.beginTransaction()
                ?.addSharedElement(animationTarget, animationTarget.transitionName)
                ?.replace(R.id.main_container, cityFragment, TAG_TAB_CITIES)
                ?.addToBackStack(TAG_TAB_CITIES)
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