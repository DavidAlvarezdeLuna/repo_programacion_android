package com.example.desafio_viaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VentanaOpcionesAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_opciones_admin)
    }

    fun AccesoGestionUsuarios(view: View){
        val homeIntent = Intent(this, VentanaGestionUsuarios::class.java).apply {
        }
        startActivity(homeIntent)
    }

    fun AccesoGestionEventos(view: View){
        val homeIntent = Intent(this, VentanaGestionEventos::class.java).apply {
        }
        startActivity(homeIntent)
    }

    fun volverOpcionesAdmin(view:View){
        finish()
    }

}