package by.paranoidandroid.cityweather.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.formatDegrees
import by.paranoidandroid.cityweather.view.activity.MainActivity
import by.paranoidandroid.cityweather.view.activity.MainActivity.Companion.TAG_TAB_CITIES
import by.paranoidandroid.cityweather.view.fragment.CityFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CityAdapter(private val context: Context?,
                  private var cities: ArrayList<Forecast<Main, Coord>> = ArrayList()
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun getItemCount() = cities.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(view = LayoutInflater
                .from(context)
                .inflate(R.layout.city_item, parent, false))

        // TODO: take out ClickListener separately
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val city = cities[position]
            val activity = context as? MainActivity
            val fm = activity?.supportFragmentManager
            val cityFragment = CityFragment.newInstance(city.id)
            fm?.beginTransaction()
                    ?.replace(R.id.main_container, cityFragment, TAG_TAB_CITIES)
                    ?.addToBackStack(null)
                    ?.commitAllowingStateLoss()
        }

        return holder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCityName.text = cities[position].name
        holder.tvDistance.text = "${cities[position].coord?.lat}, ${cities[position].coord?.lon}"
        holder.tvTemperature.text = cities[position].main?.temp?.formatDegrees()

        val requestOptions = RequestOptions()
                .placeholder(R.drawable.city_placeholder)
                .error(R.drawable.city_placeholder)
        if (context != null) {
            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(cities[position].url)
                    .into(holder.ivFlag)
        }
    }

    fun updateItems(data: List<Forecast<Main, Coord>>) {
        cities.clear()
        cities.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivFlag: ImageView = view.findViewById(R.id.iv_flag)
        var tvCityName: TextView = view.findViewById(R.id.tv_city_name)
        var tvDistance: TextView = view.findViewById(R.id.tv_distance)
        var tvTemperature: TextView = view.findViewById(R.id.tv_temperature)
    }
}