package com.example.desafio_viaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VentanaEleccionAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_eleccion_admin)
    }

    fun accesoUsuario(view: View){
        val homeIntent = Intent(this, VentanaUsuario::class.java).apply {
        }
        startActivity(homeIntent)
    }

    fun accesoAdministrador(view: View){
        val homeIntent = Intent(this, VentanaOpcionesAdmin::class.java).apply {
        }
        startActivity(homeIntent)
    }

    fun volverEleccionAdmin(view:View){
        finish()
    }

}