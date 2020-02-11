package com.example.weatherapp.currentcitytemp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.base.RecyclerViewBindingAdapter
import com.example.weatherapp.currentcitytemp.model.ForecastModel
import com.example.weatherapp.currentcitytemp.model.ThreeHoursForecastModel
import com.example.weatherapp.databinding.ListItemForecastBinding
import com.example.weatherapp.databinding.ListItemThreeHoursForecastBinding

class ThreeHourForecastAdapter:RecyclerView.Adapter<ThreeHourForecastAdapter.ViewHolder>() ,RecyclerViewBindingAdapter<ThreeHoursForecastModel> {

    private  var list:List<ThreeHoursForecastModel>?=null
    override fun setData(items: List<ThreeHoursForecastModel>) {
        this.list =items
    }




     inner class ViewHolder(val binding:ListItemThreeHoursForecastBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemThreeHoursForecastBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)

    }


    override fun getItemCount() =list?.size?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            model=list?.getOrNull(position)
        }
    }


}