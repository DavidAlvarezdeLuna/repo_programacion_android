package com.example.ejquedadas

import Adaptadores.MiAdaptadorRecyclerAsis
import Adaptadores.MiAdaptadorRecyclerCon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListadoAsistencia : AppCompatActivity() {

    private val TAG = "Daniel"

    lateinit var miRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_asistencia)

        var ev:Evento = intent.getSerializableExtra("ev") as Evento

        var seleccionado:Int=0

        Log.e(TAG,ev.asistentes.size.toString())

        var miAdapter = MiAdaptadorRecyclerAsis(ev.asistentes, this)
        miRecyclerView = findViewById(R.id.rvAsistentes) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        //var miAdapter = MiAdaptadorRecycler(miArray, this)
        miRecyclerView.adapter = miAdapter


    }

    fun volver(view:View){
        finish()
    }

}