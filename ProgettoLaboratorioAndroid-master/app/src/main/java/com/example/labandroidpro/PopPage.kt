package com.example.labandroidpro

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class PopPage:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.alarm_info)

        val b = intent.extras
        val string = b?.getString("nome")


        var ogg:Alarm =FileManagement.getAlarmByName(string.toString())

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width*0.8).toInt(), (height*0.6).toInt())


        val casella_ind = findViewById<TextView>(R.id.tw_indirizzo)
        val casella_nome = findViewById<TextView>(R.id.tw_nome)

        val lun = findViewById<Button>(R.id.bt_lunedì)
        val mar = findViewById<Button>(R.id.bt_martedì)
        val mer = findViewById<Button>(R.id.bt_mercoledì)
        val gio = findViewById<Button>(R.id.bt_giovedì)
        val ven = findViewById<Button>(R.id.bt_venerdì)
        val sab = findViewById<Button>(R.id.bt_sabato)
        val dom = findViewById<Button>(R.id.bt_domenica)

        val elimina = findViewById<ImageButton>(R.id.bt_Elimina)


        casella_nome.text = ogg.getName();
        casella_ind.text = ogg.getAddress();

        var giorniAttivi = ogg.getDays();
        if(!giorniAttivi[1])
        {
            lun.setBackgroundColor(Color.GRAY)
        }
        if(!giorniAttivi[2])
        {
            mar.setBackgroundColor(Color.GRAY)
        }
        if(!giorniAttivi[3])
        {
            mer.setBackgroundColor(Color.GRAY)
        }
        if(!giorniAttivi[4])
        {
            gio.setBackgroundColor(Color.GRAY)
        }
        if(!giorniAttivi[5])
        {
            ven.setBackgroundColor(Color.GRAY)
        }
        if(!giorniAttivi[6])
        {
            sab.setBackgroundColor(Color.GRAY)
        }
        if(!giorniAttivi[0])
        {
            dom.setBackgroundColor(Color.GRAY)
        }
        elimina.setOnClickListener {
            FileManagement.deleteAlarmByName(ogg.getName())
            Toast.makeText(this, "Elemento eliminato", Toast.LENGTH_SHORT).show()
            finish()
        }
        elimina.setOnClickListener()
        {
            FileManagement.deleteAlarmByName(ogg.getName())
            Toast.makeText(
                this.applicationContext,
                "Sveglia eliminata, prego aggiornare la pagina",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
