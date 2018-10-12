package by.paranoidandroid.cityweather.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.db.room.entity.City
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CityAdapter(private val context: Context?,
                  private var cities: ArrayList<City> = ArrayList()
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun getItemCount() = cities.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(view = LayoutInflater
                    .from(context)
                    .inflate(R.layout.city_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCityName.text = cities[position].name
        holder.tvDistance.text = cities[position].country
        holder.tvTemperature.text = cities[position].temperature

        val requestOptions = RequestOptions()
                .placeholder(R.drawable.city_placeholder)
                .error(R.drawable.city_placeholder)
        if (context != null) {
            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(cities[position].url)
                    .into(holder.ivFlag)
        }
    }

    fun updateItems(data: List<City>) {
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