package com.example.labandroidpro.Fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.labandroidpro.CheckPositionService
import com.example.labandroidpro.FileManagement
import com.example.labandroidpro.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



class MapsFragment() : Fragment(), LocationListener {

    private lateinit var locationManager: LocationManager
    private lateinit var locasionManager: CheckPositionService
    var locationchanged: Boolean = false
    private lateinit var NewLocation:Location
    private val locationPermissionCode = 2



    @RequiresApi(Build.VERSION_CODES.Q)
    private val callback = OnMapReadyCallback { googleMap ->

        val zoomLevel = 12.0f
        val sydney = LatLng(-34.0, 151.0)
        lateinit var locationManager: LocationManager

        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
        //DrawCircle(sydney,googleMap)
        getLocation(googleMap)
        Crea_I_Marker(googleMap)
        if(locationchanged)
        {
            position(googleMap,NewLocation)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        // Create GeoLocationManager

    ): View? {
        locasionManager = CheckPositionService(activity as Context)
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)



    }
    fun Crea_I_Marker(googleMap: GoogleMap)
    {
        var lista_Punti = FileManagement.getAlarms()
        if(lista_Punti!=null)
        {
            for (i in lista_Punti)
            {

                val attivo = i.getActive()
                if(attivo)
                {
                    val posizione = LatLng(i.getLatitude(),i.getLongitude())
                    googleMap.addMarker(MarkerOptions().position(posizione))
                    DrawCircle(posizione,googleMap, i.getRadius())
                }
            }
        }
    }

    fun position(googleMap: GoogleMap,locationGPS: Location)
    {
        val casa = LatLng(locationGPS.latitude,locationGPS.longitude)
        //googleMap.addMarker(MarkerOptions().position(casa))
        //DrawCircle(casa,googleMap)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(casa, 12.0f))

    }

    fun SetFocus(googleMap: GoogleMap,location:LatLng)
    {
        val posizione = location
        googleMap.addMarker(MarkerOptions().position(posizione))
        DrawCircle(posizione,googleMap,10.0)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posizione, 12.0f))
    }
    private fun DrawCircle(Marker: LatLng,mappa: GoogleMap, radius: Double) {
        //draw a circle
        val circly = CircleOptions()
        circly.center(Marker)
        circly.radius(radius)
        mappa.addCircle(circly)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingPermission")
    private fun getLocation(googleMap: GoogleMap) {
        locationManager = requireActivity().baseContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(
                requireActivity().baseContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                locationPermissionCode
            )
            while(ContextCompat.checkSelfPermission(
                    requireActivity().baseContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED){
                Log.i("MapsFragment", "In attesa di ACCESS_FINE_LOCATION")
            }
            if(ContextCompat.checkSelfPermission(
                    requireActivity().baseContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    requireActivity().baseContext,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    activity as Activity,
                    arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                    locationPermissionCode)
            }
        }
        while(ContextCompat.checkSelfPermission(
                requireActivity().baseContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED){
            Log.i("MapsFragment", "In attesa delle autorizzazioni")
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        val criteria = Criteria()
        var locationGPS: Location?=null
        try {
            locationGPS=
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
        }catch(e:Exception){
            locationGPS=Location("")
            locationGPS.latitude=0.0
            locationGPS.latitude=0.0
        }
        position(googleMap, locationGPS!!)

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireActivity().baseContext, "Permission Granted", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireActivity().baseContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLocationChanged(p0: Location) {
        locationchanged = true
        NewLocation= p0
    }

    companion object{
        fun obtainPermissions(activity: Activity, locationPermissionCode: Int){
        }
    }
}
