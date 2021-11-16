package com.example.micolornote

import Adaptadores.MiAdaptadorRecycler
import Auxiliar.ConexionBBDD
import Modelo.Anotacion
import Modelo.Tarea
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Ventana_lista_tareas : AppCompatActivity() {

    //recyclerView
    lateinit var recycler_tars : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_lista_tareas)

        var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

        var posicion:String = intent.getSerializableExtra("elegido") as String
        var int_pos:Int = posicion.toInt()-1

        var tareas: ArrayList<Tarea> = ConexionBBDD.obtenerTareas(this, int_pos)

        //recyclerView
        recycler_tars = findViewById(R.id.recyclerView_tareas) as RecyclerView
        recycler_tars.setHasFixedSize(true)
        recycler_tars.layoutManager = LinearLayoutManager(this)

        var miAdapter = MiAdaptadorRecycler(ConexionBBDD.obtenerTareas(this,int_pos), this, this)

        recycler_tars.adapter = miAdapter

        //var txt_plantilla: TextView = findViewById(R.id.txt_texto_nota)
        //txt_plantilla.text = anots.get(int_pos).texto_nota

        var imagen_fondo_lienzo:ImageView = findViewById(R.id.img_fondo_lienzo)

        if(anots.get(int_pos).plantilla == "blanca"){
            imagen_fondo_lienzo.setBackgroundResource(R.drawable.blanco)
        }else{
            if(anots.get(int_pos).plantilla == "rosa") {
                imagen_fondo_lienzo.setBackgroundResource(R.drawable.rosa)
            }else{
                if(anots.get(int_pos).plantilla == "azul") {
                    imagen_fondo_lienzo.setBackgroundResource(R.drawable.azul)
                }
            }
        }

        var texto_titulo_tarea:TextView = findViewById(R.id.txt_titulo_tarea)
        var boton_crear_tarea: FloatingActionButton = findViewById(R.id.btn_crear_tarea)

        texto_titulo_tarea.doAfterTextChanged {

            if(texto_titulo_tarea.text.trim().length>0){
                boton_crear_tarea.isEnabled = true
            }else{
                boton_crear_tarea.isEnabled = false
            }

        }

        recycler_tars.setOnClickListener(){

        }
    }

    fun crear_tarea(view: View){

        var posicion:String = intent.getSerializableExtra("elegido") as String
        var int_pos:Int = posicion.toInt()-1

        var texto_titulo_tarea:TextView = findViewById(R.id.txt_titulo_tarea)
        ConexionBBDD.addTarea(this, texto_titulo_tarea.text.toString(),int_pos)

        var intentV1 = Intent(this,Ventana_lista_tareas::class.java)
        intentV1.putExtra("elegido",(int_pos+1).toString())
        startActivity(intentV1)

        finish()

        Toast.makeText(this,"Tarea " + texto_titulo_tarea.text.toString() + " añadida",Toast.LENGTH_LONG).show()
    }

    fun volver(view: View){

        var intentV1 = Intent(this,MainActivity::class.java)
        startActivity(intentV1)

        finish()
    }

}