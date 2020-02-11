package com.example.weatherapp.utils

import android.location.Location

interface OnLocationChanged {

   fun onLocationChanged(location: Location)
}