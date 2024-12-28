package com.example.labandroidpro


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.labandroidpro.Fragment.Alarm_list
import com.example.labandroidpro.Fragment.MapsFragment
import android.view.View

import java.io.File



class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "GeoWaker"

        val posizionisalvate = findViewById<ImageButton>(R.id.ib_Sveglie)
        val home = findViewById<ImageButton>(R.id.ib_Home)
        val addnew = findViewById<ImageButton>(R.id.ib_extra)
        val intentLocation = Intent(this, LocationService::class.java)

        posizionisalvate?.setOnClickListener {
            val trasaction = supportFragmentManager.beginTransaction()
            val contesto = this.baseContext;
            trasaction.replace(R.id.Contenitore, Alarm_list(contesto))
            trasaction.commit()
        }
        home?.setOnClickListener {
            val trasaction = supportFragmentManager.beginTransaction()
            trasaction.replace(R.id.Contenitore, MapsFragment())
            trasaction.commit()
        }

        addnew?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                // create an intent to switch to second activity upon clicking
                val intent = Intent(this@MainActivity, New_position::class.java)
                startActivity(intent)
            }
        })
        var string: File = this.getFilesDir()
        FileManagement.nuovo(string);
        New_position.obtainMainActivity(this)
        AlarmClock.button=home
        startService(intentLocation)
    }
}