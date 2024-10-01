package com.balsha.forecasttask.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.balsha.forecasttask.R
import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.databinding.CitiesItemBinding
import java.util.Locale

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {

    private val mCitiesList = mutableListOf<CityModel>()
    private var mListener: IOnCitySelected? = null

    fun setItems(cities: List<CityModel>) {
        mCitiesList.clear()
        mCitiesList.addAll(cities)
        notifyItemRangeInserted(0, mCitiesList.size)
    }

    fun setOnCitySelectedListener(listener: IOnCitySelected) {
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CitiesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cities_item, parent, false)
    )

    override fun getItemCount() = mCitiesList.size

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val binding = CitiesItemBinding.bind(holder.itemView)
        val context = binding.root.context
        val city = mCitiesList[position]
        val lang = Locale.getDefault().language

        binding.tvCitiesItemName.text = if (lang == "en") city.cityNameEn else city.cityNameAr

        binding.tvCitiesItemName.setBackgroundColor(
            if (position % 2 == 0)
                ContextCompat.getColor(context, R.color.colorAccent)
            else ContextCompat.getColor(context, R.color.white)
        )

        binding.viewCitiesItemLine.visibility =
            if (position == mCitiesList.lastIndex) GONE else VISIBLE

        binding.tvCitiesItemName.setOnClickListener {
            mListener?.setOnCitySelected(position)
        }
    }

    inner class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface IOnCitySelected {
        fun setOnCitySelected(position: Int)
    }
}