package com.example.weatherapp.base

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



/*
*
* Created binding adapter to set data on adapter using data binding
* */
@BindingAdapter("data")
fun <T> setAdapter(recyclerView: RecyclerView,items:List<T>?){
        if(recyclerView.adapter is RecyclerViewBindingAdapter<*> && items!=null){
            (recyclerView.adapter as RecyclerViewBindingAdapter<T>).setData(items)
        }
}

/*
*
* Created adapter to load image from the URL
* */
@BindingAdapter("loadImage")
fun loadImage(imageView:AppCompatImageView,url:String){
    Glide.with(imageView.context).load(url).into(imageView)
}