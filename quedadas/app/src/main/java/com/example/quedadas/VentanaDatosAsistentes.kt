package com.example.quedadas

import Adaptadores.MiAdaptadorRecyclerAsistentes
import Adaptadores.MiAdaptadorRecyclerConsultas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VentanaDatosAsistentes : AppCompatActivity() {

    var seleccionado:Int=0

    //RecyclerView
    lateinit var recycler_asistencias : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_datos_asistentes)

        //recyclerView
        var eve:Evento = intent.getSerializableExtra("even") as Evento
        var miAdapter = MiAdaptadorRecyclerAsistentes(eve.lista_asistencias, this)

        recycler_asistencias = findViewById(R.id.recyclerViewAsistencias) as RecyclerView
        recycler_asistencias.setHasFixedSize(true)
        recycler_asistencias.layoutManager = LinearLayoutManager(this)

        recycler_asistencias.adapter = miAdapter

    }

    fun volver(view: View){
        finish()
    }
}