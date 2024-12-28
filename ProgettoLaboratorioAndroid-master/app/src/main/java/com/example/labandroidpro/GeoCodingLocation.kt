package com.example.labandroidpro

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import java.io.IOException
import java.util.*
class GeoCodingLocation {
    private val TAG = "GeoCodeLocation"
    private var latitude=0.0
    private var longitude=0.0
    fun getAddressFromLocation(locationAddress: String, context: Context, handler: Handler) {
        val geoCoder = Geocoder(context,Locale.getDefault())
        try {
            val addressList = geoCoder.getFromLocationName(locationAddress, 1)
            if (addressList != null && addressList.size > 0) {
                val address = addressList.get(0) as Address
                latitude=address.latitude
                longitude=address.longitude
            }
        } catch (e: IOException) {
            Log.e(TAG, "Unable to connect to GeoCoder", e)
        }
    }
    fun getLatitude(): Double{
        return this.latitude
    }
    fun getLongitude():Double{
        return this.longitude
    }
}