package com.example.weatherapp.currentcitytemp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.base.RecyclerViewBindingAdapter
import com.example.weatherapp.currentcitytemp.model.ForecastModel
import com.example.weatherapp.databinding.ListItemForecastBinding

class CurrentCityForecastAdapter:RecyclerView.Adapter<CurrentCityForecastAdapter.ViewHolder>() ,RecyclerViewBindingAdapter<ForecastModel> {

    private var list:List<ForecastModel>?=null
    override fun setData(items: List<ForecastModel>) {
        this.list =items
        notifyDataSetChanged()
    }




     inner class ViewHolder(val binding:ListItemForecastBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemForecastBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)

    }


    override fun getItemCount() =list?.size?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            rvHoursForecast.adapter =ThreeHourForecastAdapter()
            model=list?.getOrNull(position)
        }
    }


}