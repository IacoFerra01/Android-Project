package com.example.labandroidpro

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.Exception
import java.util.Calendar
import kotlin.concurrent.thread


class LocationService : Service(), LocationListener {
    private lateinit var locationManager: LocationManager
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationListener: LocationListener
    var locationchanged: Boolean = false
    var location: Location?=null
    private lateinit var NewLocation:Location
    private var dayOfWeek= Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    override fun onCreate() {
        super.onCreate()
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener= LocationListener {  }
        for(i in FileManagement.getAlarms().indices){
            used.add(false)
        }
        Log.i("LocationService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("LocationService", "Service started")





        if(!Calendar.getInstance().get(Calendar.DAY_OF_WEEK).equals(dayOfWeek))
            dayOfWeek=Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            var sadas=ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
            try {
                location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
            }catch(e: Exception){
                location=Location("")
                location!!.latitude=0.0
                location!!.latitude=0.0
            }
            locationchanged=false
        }
        var alarms=FileManagement.getAlarms()
        if(alarms.size!=used.size){
            used= mutableListOf<Boolean>()
            for(i in alarms.indices){
                used.add(false)
            }
        }
        for(i in alarms.indices){
            Log.i("LocationService", alarms[i].getName())
            var test=alarms[i].getActive()
            test=alarms[i].getDays()[dayOfWeek-1]
            test=alarms[i].inArea(location!!.latitude, location!!.longitude)
            test=used[i]
            if(alarms[i].getActive()&&alarms[i].getDays()[dayOfWeek-1]&&alarms[i].inArea(location!!.latitude, location!!.longitude)&&!used[i]){
                used[i]=true
                Log.i("LocationService", "Dentro la posizione")
                var mp: MediaPlayer = MediaPlayer.create(this, R.raw.alarmclock)
                mp.setLooping(true)
                mp.start()
                AlarmClock.mp=mp
                var intentalarm= Intent(applicationContext, AlarmClock::class.java)
                intentalarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intentalarm)
            }
            else{
                Log.i("LocationService", "posizione:"+location!!.latitude.toString()+","+location!!.longitude.toString())
            }
        }

        thread {
            Looper.prepare()
            while (true) {
                if(!Calendar.getInstance().get(Calendar.DAY_OF_WEEK).equals(dayOfWeek))
                    dayOfWeek=Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
                    try {
                        location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
                    }catch(e: Exception){
                        location=Location("")
                        location!!.latitude=0.0
                        location!!.latitude=0.0
                    }
                    locationchanged=false
                }
                var alarms=FileManagement.getAlarms()
                if(alarms.size!=used.size){
                    used= mutableListOf<Boolean>()
                    for(i in alarms.indices){
                        used.add(false)
                    }
                }
                for(i in alarms.indices){
                    Log.i("LocationService", alarms[i].getName())
                    if(alarms[i].getActive()&&alarms[i].getDays()[dayOfWeek-1]&&alarms[i].inArea(location!!.latitude, location!!.longitude)&&!used[i]){
                        used[i]=true
                        Log.i("LocationService", "Dentro la posizione")
                        var mp: MediaPlayer = MediaPlayer.create(this, R.raw.alarmclock)
                        mp.setLooping(true)
                        mp.start()
                        AlarmClock.mp=mp
                        var intentalarm= Intent(applicationContext, AlarmClock::class.java)
                        intentalarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intentalarm)
                    }
                    else{
                        Log.i("LocationService", "posizione:"+location!!.latitude.toString()+","+location!!.longitude.toString())
                    }
                }
                Thread.sleep(1000)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LocationService", "Service destroyed")
    }

    override fun onLocationChanged(p0: Location) {
        locationchanged = true
        NewLocation= p0
    }

    companion object{
        var used = mutableListOf<Boolean>()
    }
}