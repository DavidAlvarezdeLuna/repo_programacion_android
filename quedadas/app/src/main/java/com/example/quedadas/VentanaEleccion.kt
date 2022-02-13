package com.example.quedadas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VentanaEleccion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_eleccion)
    }

    fun editar(view: View){
        var intentV1 = Intent(this,VentanaEdicion::class.java)
        startActivity(intentV1)
        finish()
    }

    fun consultar(view: View){
        var intentV1 = Intent(this,VentanaConsulta::class.java)
        startActivity(intentV1)
        finish()
    }

    fun cerrar(view:View){
        finish()
    }

}