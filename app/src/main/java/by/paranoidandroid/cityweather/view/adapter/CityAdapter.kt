package by.paranoidandroid.cityweather.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import by.paranoidandroid.cityweather.Logger.TAG
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.view.activity.MainActivity
import by.paranoidandroid.cityweather.view.activity.TAG_1
import by.paranoidandroid.cityweather.view.activity.TAG_CITY
import by.paranoidandroid.cityweather.view.fragment.CityFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CityAdapter(private val context: Context?,
                  private var cities: ArrayList<Forecast<Main>> = ArrayList()
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun getItemCount() = cities.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(view = LayoutInflater
                .from(context)
                .inflate(R.layout.city_item, parent, false))

        /*holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            Log.d(TAG, "1 Selected city is ${cities[position].name}")
            onClick(position)
        }*/

        //holder.itemView.setOnClickListener = onClick(holder.adapterPosition)

        // TODO: create ClickListener separately
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            Log.d(TAG, "Selected city is ${cities[position].name}")
            val city = cities[position]
            // hide old fragment, CitiesFragment, and show new Fragment, CityFragment
            val activity = context as? MainActivity
            val fm = activity?.supportFragmentManager
            val currentFragment = fm?.findFragmentByTag(TAG_1)
            val cityFragment = CityFragment.newInstance(city.id)
            fm?.beginTransaction()
                    ?.hide(currentFragment)
                    ?.add(R.id.main_container, cityFragment, TAG_CITY)
                    ?.addToBackStack(TAG_CITY)
                    ?.commitAllowingStateLoss()
        }

        return holder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCityName.text = cities[position].name
        /*holder.tvDistance.text = cities[position].country
        holder.tvTemperature.text = cities[position].temperature*/

        val requestOptions = RequestOptions()
                .placeholder(R.drawable.city_placeholder)
                .error(R.drawable.city_placeholder)
        if (context != null) {
            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(cities[position].url)
                    .into(holder.ivFlag)
        }
    }

    fun updateItems(data: List<Forecast<Main>>) {
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