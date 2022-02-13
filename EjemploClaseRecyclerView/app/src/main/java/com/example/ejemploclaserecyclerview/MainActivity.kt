package com.example.ejemploclaserecyclerview

import Adaptadores.MiAdaptadorRecycler
import Modelo.FactoriaListaPersonaje
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    var personajes = FactoriaListaPersonaje.generaLista(12)

    /**
     * El recyclerview lo tenemos que instanciar en el método setUpRecyclerView() por lo que tenemos que
     * ponerle la propiedad lateinit a la variable, indicándole a Kotlin que la instanciaremos más tarde.
     */
    lateinit var miRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("Fernando", personajes.toString())

        miRecyclerView = findViewById(R.id.listaPersonajesRecycler) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        var miAdapter = MiAdaptadorRecycler(personajes, this)
        miRecyclerView.adapter = miAdapter

    }
}