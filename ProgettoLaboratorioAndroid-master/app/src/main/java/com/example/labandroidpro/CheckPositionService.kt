package com.example.labandroidpro

import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
class CheckPositionService(context: Context) {
    private val context: Context = context
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequest= LocationRequest()
    private var startedLocationTracking = false
    init {
        configureLocationRequest()
        setupLocationProviderClient()
    }
    private fun setupLocationProviderClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }
    private fun configureLocationRequest() {
        locationRequest.interval = UPDATE_INTERVAL_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_MILLISECONDS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    fun startLocationTracking(locationCallback: LocationCallback) {
        if (!startedLocationTracking) {
            //noinspection MissingPermission
            fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper())
            this.locationCallback = locationCallback
            startedLocationTracking = true
        }
    }

    companion object {
        const val UPDATE_INTERVAL_MILLISECONDS: Long = 0
        const val FASTEST_UPDATE_INTERVAL_MILLISECONDS = UPDATE_INTERVAL_MILLISECONDS / 2
    }
}