package com.example.labandroidpro

import android.location.Location


class Alarm{

    //Il salvataggio su file dell'alarm è possibile grazie ad una serie di dati costiuiti nel seguente modo
    //Nome_Alarm║Giorni_attivi║Latitudine_posizione║Longitudine_posizione║Raggio(m)║Stato_alarm║Indirizzo_Posizione
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var days: Array<Boolean> = arrayOf(false, false, false, false, false, false, false)
    private var name: String = "";

    private var radius: Double = 0.0
    private var active: Boolean=true
    private var address: String=""

    constructor(){
    }

    constructor(la: Double, lo: Double, d: Array<Boolean>, n: String, r: Double, a:Boolean){
        latitude=la
        longitude=lo
        days=d
        name=n
        radius=r
        active=a
    }

    fun getName():String
    {
        return this.name
    }

    fun setName(input: String)
    {
        this.name = input;
    }
    fun getLatitude(): Double{
        return this.latitude
    }
    fun setLatitude(input: Double){
        this.latitude=input
    }

    fun getLongitude(): Double{
        return this.longitude
    }
    fun setLongitude(input: Double){
        this.longitude=input
    }
    fun getDays(): Array<Boolean>{
        return this.days
    }
    fun setDays(input: Array<Boolean>){
        this.days=input
    }
    fun getRadius(): Double{
        return this.radius
    }
    fun setRadius(input: Double){
        this.radius=input
    }

    fun getActive(): Boolean {
        return this.active
    }

    fun setActive(input: Boolean){
        this.active=input
    }

    fun setAddress(input: String){
        this.address=input
    }

    fun getAddress(): String{
        return this.address
    }

    fun inArea(lat: Double, long: Double): Boolean{
        val results = FloatArray(1)
        Location.distanceBetween(
            this.latitude,
            this.longitude,
            lat,
            long,
            results
        )
        val distanceInMeters = results[0]
        return distanceInMeters < this.radius
    }
}
