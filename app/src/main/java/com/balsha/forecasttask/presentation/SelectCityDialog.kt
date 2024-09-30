package com.balsha.forecasttask.presentation

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.balsha.forecasttask.R
import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.presentation.CitiesAdapter.IOnCitySelected

class SelectCityDialog(context: Context) : Dialog(context), IOnCitySelected {

    private val mCitiesAdapter by lazy { CitiesAdapter() }
    private val mCitiesList = mutableListOf<CityModel>()
    private var mListener: IOnCitySelected? = null

    private var rvSelectCityItems: RecyclerView? = null
    private var ibSelectCityClose: ImageButton? = null

    fun setItems(cities: List<CityModel>) {
        mCitiesList.clear()
        mCitiesList.addAll(cities)
//        mCitiesAdapter.notifyItemRangeInserted(0, mCitiesList.size)
    }

    fun setOnCitySelectedListener(listener: IOnCitySelected) {
        this.mListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_select_city)
        window!!.setLayout(MATCH_PARENT, WRAP_CONTENT)
        window!!.setGravity(Gravity.CENTER)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)

        ibSelectCityClose = findViewById(R.id.ibSelectCityClose)
        rvSelectCityItems = findViewById(R.id.rvSelectCityItems)

        setDialogOptions()
    }

    override fun setOnCitySelected(position: Int) {
        mListener?.setOnCitySelected(position)
        dismiss()
    }

    private fun setDialogOptions() {
        ibSelectCityClose?.setOnClickListener { dismiss() }

        rvSelectCityItems?.adapter = mCitiesAdapter
        mCitiesAdapter.setItems(mCitiesList)
        mCitiesAdapter.setOnCitySelectedListener(this)
    }
}