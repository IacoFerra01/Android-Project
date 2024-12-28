package com.example.labandroidpro

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class AlarmClock : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_clock)
        val turnOff=findViewById<Button>(R.id.turnOff)
        turnOff.setOnClickListener{
            mp.stop()
            finish()
        }
    }
    companion object{
        lateinit var button: ImageButton
        lateinit var mp:MediaPlayer
    }
}