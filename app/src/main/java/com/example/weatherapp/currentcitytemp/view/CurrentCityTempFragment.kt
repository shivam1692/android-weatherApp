package com.example.weatherapp.currentcitytemp.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.base.MyApplication
import com.example.weatherapp.base.WeatherRepo

import com.example.weatherapp.currentcitytemp.adapter.CurrentCityForecastAdapter
import com.example.weatherapp.currentcitytemp.repository.Repository
import com.example.weatherapp.currentcitytemp.viewmodel.CurrentCityTempViewModel
import com.example.weatherapp.currentcitytemp.viewmodel.ViewModelFactory
import com.example.weatherapp.databinding.CurrentCityTempFragmentBinding
import com.example.weatherapp.network.ApiClient
import com.example.weatherapp.utils.AppConstants
import com.example.weatherapp.utils.AppLocationManager
import com.example.weatherapp.utils.OnLocationChanged
import com.example.weatherapp.utils.showToast
import kotlinx.android.synthetic.main.layout_error.view.*
import javax.inject.Inject

class CurrentCityTempFragment : Fragment() {

    companion object {
        fun newInstance() = CurrentCityTempFragment()
    }

    @Inject
    lateinit var repository: WeatherRepo

    private lateinit var viewModel: CurrentCityTempViewModel
    private lateinit var binding:CurrentCityTempFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View? {
        binding = CurrentCityTempFragmentBinding.inflate(inflater, container,false)
        binding.view=this
        binding.lifecycleOwner=this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MyApplication.get(context).appComponent.inject(this)
        viewModel = ViewModelProvider(this,ViewModelFactory(repository,activity!!.application)).get(CurrentCityTempViewModel::class.java)
        binding.viewModel=viewModel
        handleClickListener()
        setRecyclerView()
        if(Build.VERSION.SDK_INT>=23){
            checkLocationPermission()
        }else{
            getLocationUpdates()
        }
    }



    private fun setRecyclerView(){
        with(binding.rvDaysForecast){
            layoutManager = LinearLayoutManager(activity)
            adapter = CurrentCityForecastAdapter()
        }

    }

    private fun handleClickListener(){
        binding.errorLayout.btnRetry.setOnClickListener{

        }
    }

    private fun callApi(lat:String,lon:String){
            viewModel.getWeatherForecast(lat,lon)
    }

    /**
     * Check the permission required to fetch location updates.
     *
     */
    private fun checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),AppConstants.LOCATION_PERMISSION_REQUEST_CODE)

        }else{
            getLocationUpdates()
        }
    }

    /**
     * Subscribe to the location updates. On current location fetched an API will be called
     * to fetch weather data.
     *
     */
    private fun getLocationUpdates(){
        var subscriberId=0
        val locationManager = AppLocationManager.getInstance(activity!!)
        subscriberId = locationManager.subscribeLocationUpdates(object: OnLocationChanged {
            override fun onLocationChanged(location: Location) {
                locationManager.unSubscribeLocationUpdates(subscriberId)
                callApi(location.latitude.toString(),location.longitude.toString())
            }

        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==AppConstants.LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLocationUpdates()
            }else{
                getString(R.string.location_permission_denied).showToast(context)
                findNavController().navigateUp()
            }
        }
    }



}
