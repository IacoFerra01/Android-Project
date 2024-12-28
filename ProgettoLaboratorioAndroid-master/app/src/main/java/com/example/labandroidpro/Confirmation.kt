package com.example.labandroidpro

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class Confirmation : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val b = intent.extras
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width*0.8).toInt(), (height*0.6).toInt())


        val text=findViewById<TextView>(R.id.tw_nome)
        val yes=findViewById<ImageButton>(R.id.yes)
        val no=findViewById<ImageButton>(R.id.no)

        text.text="Modificare "+ alarm.getName()+" ?"

        yes.setOnClickListener {
            FileManagement.updateAlarmByName(alarm.getName(), alarm)
            Toast.makeText(this.applicationContext, "Modifica effettuata", Toast.LENGTH_SHORT).show()
            finish()
            father.finish()
        }
        no.setOnClickListener{
            Toast.makeText(this.applicationContext, "È già presente una sveglia con quel nome", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    companion object{
        var alarm= Alarm()
        lateinit var father:Activity
    }
}