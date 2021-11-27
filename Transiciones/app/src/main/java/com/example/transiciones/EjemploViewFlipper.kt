package com.example.transiciones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import android.widget.ViewFlipper

class EjemploViewFlipper : AppCompatActivity() {
    lateinit var btnS : Button
    lateinit var btnA : Button
    lateinit var mvf : ViewFlipper

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Transiciones)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejemplo_view_flipper)

        btnS = findViewById(R.id.btnSigFlipper)
        btnA = findViewById(R.id.btnAntFlipper)
        mvf = findViewById(R.id.vfMiViewFlipper)
    }

    fun sig(view: View){
        mvf.showNext()
        mvf.stopFlipping()
    }

    fun prev(view:View){
        mvf.showPrevious()
    }
}