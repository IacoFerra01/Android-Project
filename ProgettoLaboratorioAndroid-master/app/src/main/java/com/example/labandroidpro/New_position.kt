package com.example.labandroidpro

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.labandroidpro.Fragment.MapsFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.*

class New_position : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_position)

        var sett: Array<Boolean> = arrayOf(false, false, false, false, false, false, false)
        var valore  = 0.0

        val salva = findViewById<ImageButton>(R.id.bt_salva)
        val annulla = findViewById<ImageButton>(R.id.bt_pulisci)

        val lun = findViewById<Button>(R.id.bt_lunedì)
        val mar = findViewById<Button>(R.id.bt_martedì)
        val mer = findViewById<Button>(R.id.bt_mercoledì)
        val gio = findViewById<Button>(R.id.bt_giovedì)
        val ven = findViewById<Button>(R.id.bt_venerdì)
        val sab = findViewById<Button>(R.id.bt_sabato)
        val dom = findViewById<Button>(R.id.bt_domenica)

        val nome = findViewById<EditText>(R.id.Edited_Name)
        val indirizzo = findViewById<EditText>(R.id.et_indirizzo)
        val range = findViewById<SeekBar>(R.id.sb_Raggio)


        val b = intent.extras
        val string = b?.getString("nome")
        if(b!=null)
        {
            var ogg:Alarm =FileManagement.getAlarmByName(string.toString())
            nome.setText(ogg.getName())
            indirizzo.setText(ogg.getAddress())

            var giorniAttivi = ogg.getDays()
            if(giorniAttivi[1])
            {
                lun.setBackgroundColor(resources.getColor(R.color.Default_green))
            }
            if(giorniAttivi[2])
            {
                mar.setBackgroundColor(resources.getColor(R.color.Default_green))
            }
            if(giorniAttivi[3])
            {
                mer.setBackgroundColor(resources.getColor(R.color.Default_green))
            }
            if(giorniAttivi[4])
            {
                gio.setBackgroundColor(resources.getColor(R.color.Default_green))
            }
            if(giorniAttivi[5])
            {
                ven.setBackgroundColor(resources.getColor(R.color.Default_green))
            }
            if(giorniAttivi[6])
            {
                sab.setBackgroundColor(resources.getColor(R.color.Default_green))
            }
            if(giorniAttivi[0])
            {
                dom.setBackgroundColor(resources.getColor(R.color.Default_green))
            }
            range.progress = ogg.getRadius().toInt()

            val point = LatLng(ogg.getLatitude(),ogg.getLongitude())

            val googleMap = null
            /*
            googleMap?.addMarker(MarkerOptions().position(point))
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 12.0f))

             */



        }

        salva.setOnClickListener{
            //Salvataggio di prova
            val locationAddress = GeoCodingLocation()
            locationAddress.getAddressFromLocation(indirizzo.text.toString(), applicationContext,
                GeoCoderHandler())
            val alarm = Alarm()//(FileManagement.getLastAlarm().id + 1))
            alarm.setDays(sett)
            val stringa = nome.text.toString()
            if((nome.text.toString() != "")&&(indirizzo.text.toString()!="")) {
                alarm.setName(stringa)
                alarm.setRadius(valore)
                alarm.setLatitude(locationAddress.getLatitude())
                alarm.setLongitude(locationAddress.getLongitude())
                alarm.setAddress(indirizzo.text.toString())
                if (FileManagement.getAlarmByName(alarm.getName()).getName().equals("")) {
                    FileManagement.saveAlarm(alarm)
                    Toast.makeText(
                        this.applicationContext,
                        "Salvataggio effettuato",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Confirmation.alarm = alarm
                    Confirmation.father = this
                    val obj = Bundle()
                    val intent = Intent(this, Confirmation::class.java)
                    ContextCompat.startActivity(this, intent, obj)
                }
            }else
            {
                Toast.makeText(this.applicationContext,"Nome o indirizzo della sveglia mancante" ,Toast.LENGTH_SHORT).show()
            }
        }


        annulla.setOnClickListener{
            nome.text.clear()
            indirizzo.text.clear()
            range.progress = 0
            lun.setBackgroundColor(Color.rgb(5, 135, 118))
            mar.setBackgroundColor(Color.rgb(5, 135, 118))
            mer.setBackgroundColor(Color.rgb(5, 135, 118))
            gio.setBackgroundColor(Color.rgb(5, 135, 118))
            ven.setBackgroundColor(Color.rgb(5, 135, 118))
            sab.setBackgroundColor(Color.rgb(5, 135, 118))
            dom.setBackgroundColor(Color.rgb(5, 135, 118))
        }



//listener per ogni giorno della settimana che acquisisce il giorno e cambia il bg del bottone selezionato
        lun.setOnClickListener {
            if(!sett[1]) {
                lun.setBackgroundColor(Color.GREEN)
                sett[1] = true
            }
            else
            {
                //da poi decidere il colore
                lun.setBackgroundColor(resources.getColor(R.color.Default_green))
                sett[1] = false
            }
        }

        mar.setOnClickListener {
            if(!sett[2]) {
                mar.setBackgroundColor(Color.GREEN)
                sett[2] = true
            }
            else
            {
                //da poi decidere il colore
                mar.setBackgroundColor(resources.getColor(R.color.Default_green))
                sett[2] = false
            }

            //var stringa = FileManagement.printAlarm()
            //Toast.makeText(this, stringa, Toast.LENGTH_SHORT).show()
        }

        mer.setOnClickListener {
            if(!sett[3]) {
                mer.setBackgroundColor(Color.GREEN)
                sett[3] = true
            }
            else
            {
                //da poi decidere il colore
                mer.setBackgroundColor(resources.getColor(R.color.Default_green))
                sett[3] = false
            }
        }

        gio.setOnClickListener {
            if(!sett[4]) {
                gio.setBackgroundColor(Color.GREEN)
                sett[4] = true
            }
            else
            {
                //da poi decidere il colore
                gio.setBackgroundColor(resources.getColor(R.color.Default_green))
                sett[4] = false
            }
        }

        ven.setOnClickListener {
            if(!sett[5]) {
                ven.setBackgroundColor(Color.GREEN)
                sett[5] = true
            }
            else
            {
                //da poi decidere il colore
                ven.setBackgroundColor(resources.getColor(R.color.Default_green))
                sett[5] = false
            }
        }

        sab.setOnClickListener {
            if(!sett[6]) {
                sab.setBackgroundColor(Color.GREEN)
                sett[6] = true
            }
            else
            {
                //da poi decidere il colore
                sab.setBackgroundColor(resources.getColor(R.color.Default_green))
                sett[6] = false
            }
        }

        dom.setOnClickListener {
            if(!sett[0]) {
                dom.setBackgroundColor(Color.GREEN)
                sett[0] = true
            }
            else
            {
                //da poi decidere il colore
                dom.setBackgroundColor(resources.getColor(R.color.Default_green))
                sett[0] = false
            }
        }

        range.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                valore = range.progress.toDouble()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //potrebbero essere inutili ma vanno implementati
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //potrebbero essere inutili ma vanno implementati
            }
        })

    }

    companion object {
        lateinit var mainActivity: MainActivity

        fun obtainMainActivity(a: MainActivity){
            mainActivity=a
        }

        private class GeoCoderHandler : Handler() {
            override fun handleMessage(message: Message) {
                val locationAddress: String?
                locationAddress = when (message.what) {
                    1 -> {
                        val bundle = message.data
                        bundle.getString("address")
                    }
                    else -> null
                }
            }
        }
    }
}