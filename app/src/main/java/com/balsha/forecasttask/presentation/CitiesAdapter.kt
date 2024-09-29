package com.balsha.forecasttask.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.balsha.forecasttask.R
import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.databinding.CitiesItemBinding
import java.util.Locale

class CitiesAdapter(context: Context) : ArrayAdapter<CityModel>(context, R.layout.cities_item) {

    private val mCitiesList = mutableListOf<CityModel>()
    private var listener: IOnCitySelected? = null

    fun setItems(cities: List<CityModel>) {
        mCitiesList.clear()
        mCitiesList.addAll(cities)
        notifyDataSetChanged()
    }

    fun setOnCitySelectedListener(listener: IOnCitySelected) {
        this.listener = listener
    }

    override fun getCount() = mCitiesList.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: CitiesItemBinding
        val viewHolder: ViewHolder
        val city = mCitiesList[position]

        if (convertView == null) {
            binding = CitiesItemBinding.inflate(LayoutInflater.from(context), parent, false)
            viewHolder = ViewHolder(binding)
            binding.root.tag = viewHolder
        } else {
            binding = CitiesItemBinding.bind(convertView)
            viewHolder = convertView.tag as ViewHolder
        }

        val lang = Locale.getDefault().language

        viewHolder.tvCitiesItemName.text = if (lang == "en") city.cityNameEn else city.cityNameAr

        binding.root.setOnClickListener {
            listener?.setOnCitySelected(position)
        }

        return binding.root
    }

    private class ViewHolder(binding: CitiesItemBinding) {
        val tvCitiesItemName = binding.tvCitiesItemName
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    interface IOnCitySelected {
        fun setOnCitySelected(position: Int)
    }
}