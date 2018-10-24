package by.paranoidandroid.cityweather.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import by.paranoidandroid.cityweather.R
import by.paranoidandroid.cityweather.Utils.LOG_TAG
import by.paranoidandroid.cityweather.domain.entity.Coord
import by.paranoidandroid.cityweather.domain.entity.Forecast
import by.paranoidandroid.cityweather.domain.entity.Main
import by.paranoidandroid.cityweather.formatDegrees
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CityAdapter(private val context: Context?,
                  private val clickListener: OnItemClickListener,
                  private var cities: ArrayList<Forecast<Main, Coord>> = ArrayList()
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun getItemCount() = cities.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(view = LayoutInflater
                .from(context)
                .inflate(R.layout.city_item, parent, false))

        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val city = cities[position]
            val imageView = holder.ivFlag
            imageView.transitionName = context?.getString(R.string.image_transition)
            clickListener.onClick(position, city, imageView)
        }

        return holder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCityName.text = cities[position].name
        holder.tvDistance.text = "${cities[position].coord?.lat}, ${cities[position].coord?.lon}"
        holder.tvTemperature.text = cities[position].main?.temp?.formatDegrees()
        Log.d(LOG_TAG, "url: ${cities[position].url}")

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

    interface OnItemClickListener {
        fun onClick(position: Int, city: Forecast<Main, Coord>, animationTarget: ImageView)
    }
}