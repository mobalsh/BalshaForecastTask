package com.balsha.forecasttask.presentation.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.balsha.forecasttask.BuildConfig
import com.balsha.forecasttask.R
import com.balsha.forecasttask.data.model.forecast.ForecastModel
import com.balsha.forecasttask.databinding.ForecastsItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.util.Locale

class ForecastsAdapter : RecyclerView.Adapter<ForecastsAdapter.ForecastsViewHolder>() {

    private val mForecastsList = mutableListOf<ForecastModel>()
    private var mListener: IOnForecastSelected? = null

    fun setItems(forecasts: List<ForecastModel>) {
        mForecastsList.clear()
        mForecastsList.addAll(forecasts)
        notifyItemRangeInserted(0, mForecastsList.size)
    }

    fun setOnForecastSelectedListener(listener: IOnForecastSelected) {
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ForecastsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.forecasts_item, parent, false)
    )

    override fun getItemCount() = mForecastsList.size

    override fun onBindViewHolder(holder: ForecastsViewHolder, position: Int) {
        val binding = ForecastsItemBinding.bind(holder.itemView)
        val context = binding.root.context
        val forecast = mForecastsList[position]

        binding.tvForecastsItemDate.text =
            String.format(
                Locale.ENGLISH, "%s %s",
                context.getString(R.string.date),
                forecast.dateText.split(" ").first()
            )

        val iconURL = "${BuildConfig.IMAGES_URL}${forecast.weather.first().icon}@2x.png"

        Glide.with(context).load(iconURL).override(240, 240).skipMemoryCache(true)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    e?.logRootCauses("GlideError")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable, model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(binding.ivForecastsItemIcon)

        binding.tvForecastsItemTemp.text =
            String.format(
                Locale.ENGLISH, "%s %.1f/%.1fC",
                context.getString(R.string.high_low),
                forecast.main.tempMax,
                forecast.main.tempMin
            )

        binding.cvForecastsItem.setOnClickListener {
            mListener?.setOnForecastSelected(position)
        }
    }

    inner class ForecastsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface IOnForecastSelected {
        fun setOnForecastSelected(position: Int)
    }
}