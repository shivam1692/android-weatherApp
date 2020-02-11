package com.example.weatherapp.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import org.jetbrains.annotations.NotNull
import com.example.weatherapp.R

class AppLocationManager private constructor(private var context: Activity): LocationCallback(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private var mLocationRequest: LocationRequest?=null
    private var locationSettingRequest: LocationSettingsRequest?=null
    private var googleApiClient : GoogleApiClient? = null
    private var subscribersMap = HashMap<Int,OnLocationChanged?>()
    private val LOCATIONDIALG_CONSTANT = 1111
    private val PLAY_SERVICES_RESOLUTION_REQUEST = 300

    companion object {
       private var locationManager:AppLocationManager?=null
        fun getInstance(@NotNull context: Activity):AppLocationManager{
            if(locationManager==null){
                synchronized(this){
                    if(locationManager==null){
                        locationManager = AppLocationManager(context)
                    }
                }
            }
            locationManager!!.context=context
            return locationManager!!
        }
    }

    init {
        if(checkPlayServices()) {
            initLocationRequest()
        }
    }

    private fun checkPlayServices(): Boolean {
        val resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, context, PLAY_SERVICES_RESOLUTION_REQUEST).show()
            } else {
                Toast.makeText(context, context.resources.getString(R.string.play_service_not_found_error), Toast.LENGTH_LONG).show()
            }
            return false
        }
        return true
    }

    /*
    * This method is used to initialize the location request
    * */
    private fun initLocationRequest(){
        mLocationRequest=  LocationRequest()
        mLocationRequest?.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        mLocationRequest?.interval = 20*1000
        mLocationRequest?.fastestInterval =20000

        val locationRequestBuilder = LocationSettingsRequest.Builder()
        locationRequestBuilder.addLocationRequest(mLocationRequest!!)
        locationSettingRequest = locationRequestBuilder.build()
        checkLocationSettings()
    }


    /*
    * This method is used to check location settings
    * */
    private fun checkLocationSettings(){
        val settingsClient = LocationServices.getSettingsClient(context)
        settingsClient.checkLocationSettings(locationSettingRequest)

    }

    private fun requestLocationUpdates(){
        openLocationDialog()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        val fusedLocationProviderClient  =  LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                onLocationChanged(it)
        }.addOnFailureListener {
            it.printStackTrace()
            openLocationDialog()
            Log.d("AppLocationManager","Unable to get last known location")
        }
    }


    private fun onLocationChanged(location:Location?){
        if(location!=null){
            publishLocation(location)
        }
    }


    /**
     * Method will publish location result to all subscribers
     */
    private fun publishLocation(location:Location){
        subscribersMap.values.toList().forEach {
            it?.onLocationChanged(location)
        }
    }


    override fun onLocationResult(p0: LocationResult?) {
        super.onLocationResult(p0)
        onLocationChanged(p0?.lastLocation)
    }

    /**
     * This method will allow user to subscribe location updated in the app
     *
     * @param listener OnLocationChanged interface instance
     * @return subscriberId
     */
    fun subscribeLocationUpdates(listener:OnLocationChanged?):Int{
        val id =subscribersMap.size+1
        subscribersMap[id] =listener
        requestLocationUpdates()
        return id
    }

    /**
     * This method will allow user to unsubscribe location updates
     *
     * @param subscriberId SubscriberId will be required to stop location updates for that specific class
     */
    fun unSubscribeLocationUpdates(subscriberId:Int){
        if(subscribersMap.containsKey(subscriberId)){
            subscribersMap.remove(subscriberId)
        }

        if(subscribersMap.size==0)
            stopLocationUpdates()
    }

   private fun stopLocationUpdates(){
        LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(this)
    }

    private fun openLocationDialog() {

        googleApiClient = GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build()
        googleApiClient?.connect()

        mLocationRequest = LocationRequest.create()
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest?.interval = (30 * 1000).toLong()
        mLocationRequest?.fastestInterval = (5 * 1000).toLong()
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest!!)

        //**************************
        builder.setAlwaysShow(true) //this is the key ingredient
        //**************************

        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            val state = result.locationSettingsStates
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS ->
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        getLastLocation()
                        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(mLocationRequest,this, Looper.myLooper())
                    }

                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                    // Location settings are not satisfied. But could be fixed by showing the user
                    // a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(
                                context, LOCATIONDIALG_CONSTANT)
                    } catch (e: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                }
            }// Location settings are not satisfied. However, we have no way to fix the
            // settings so we won't show the dialog.
        }
    }
    override fun onConnected(p0: Bundle?) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation()
            LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(mLocationRequest,this, Looper.myLooper())
        }
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

}