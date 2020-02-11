package com.example.weatherapp.currenttemp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.base.RecyclerViewBindingAdapter
import com.example.weatherapp.currenttemp.model.CurrentTemperatureModel
import com.example.weatherapp.databinding.ListItemCurrentTempBinding

class CurrentTempAdapter:RecyclerView.Adapter<CurrentTempAdapter.ViewHolder>(),RecyclerViewBindingAdapter<CurrentTemperatureModel> {

    private val list by lazy {
        ArrayList<CurrentTemperatureModel>()
    }
    override fun setData(items: List<CurrentTemperatureModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemCurrentTempBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)

    }

    override fun getItemCount() = list.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            model=list[position]
        }
    }


    inner class ViewHolder(val binding:ListItemCurrentTempBinding):RecyclerView.ViewHolder(binding.root)

}