package com.example.desafio_viaje

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VentanaEspera : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_espera)
    }

    fun volverEspera(view: View){
        finish()
    }

}