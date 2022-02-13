package com.example.ejquedadas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VentanaInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_inicio)
    }

    fun irEditar(view:View){
        var intentV1 = Intent(this,EditarEventos::class.java)
        startActivity(intentV1)
        finish()
    }

    fun irConsultar(view:View){
        var intentV1 = Intent(this,ConsultarEventos::class.java)
        startActivity(intentV1)
        finish()
    }

}