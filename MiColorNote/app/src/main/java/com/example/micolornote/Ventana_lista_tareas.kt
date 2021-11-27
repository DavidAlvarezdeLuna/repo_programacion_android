package com.example.micolornote

import Adaptadores.MiAdaptadorRecycler
import Auxiliar.ConexionBBDD
import Modelo.Anotacion
import Modelo.Tarea
import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class Ventana_lista_tareas : AppCompatActivity() {

    private val cameraRequest = 1888

    //recyclerView
    lateinit var recycler_tars : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_lista_tareas)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)

        var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

        var id_seleccionado:String = intent.getSerializableExtra("elegido") as String
        var int_id_seleccionado:Int = id_seleccionado.toInt()

        var texto_titulo_lista:TextView = findViewById(R.id.txt_titulo_lista)
        texto_titulo_lista.text = intent.getStringExtra("titulo") as String

        var tareas: ArrayList<Tarea> = ConexionBBDD.obtenerTareas(this, int_id_seleccionado)

        var intentAdaptador = Intent(this,Ventana_lista_tareas::class.java)

        //recyclerView
        recycler_tars = findViewById(R.id.recyclerView_tareas) as RecyclerView
        recycler_tars.setHasFixedSize(true)
        recycler_tars.layoutManager = LinearLayoutManager(this)

        var miAdapter = MiAdaptadorRecycler(ConexionBBDD.obtenerTareas(this,int_id_seleccionado), this, this, intentAdaptador)

        recycler_tars.adapter = miAdapter

        //var txt_plantilla: TextView = findViewById(R.id.txt_texto_nota)
        //txt_plantilla.text = anots.get(int_pos).texto_nota

        var imagen_fondo_lienzo:ImageView = findViewById(R.id.img_fondo_lienzo)

        for(Anotacion in anots) {
            if (Anotacion.id_anotacion == int_id_seleccionado) {

                if(Anotacion.plantilla == "blanca"){
                    imagen_fondo_lienzo.setBackgroundResource(R.drawable.blanco)
                }else{
                    if(Anotacion.plantilla == "rosa") {
                        imagen_fondo_lienzo.setBackgroundResource(R.drawable.rosa)
                    }else{
                        if(Anotacion.plantilla == "azul") {
                            imagen_fondo_lienzo.setBackgroundResource(R.drawable.azul)
                        }
                    }
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

        var id_seleccionado:String = intent.getSerializableExtra("elegido") as String
        var int_id_seleccionado:Int = id_seleccionado.toInt()

        var texto_titulo_tarea:TextView = findViewById(R.id.txt_titulo_tarea)

        //var texto_titulo_lista:TextView = findViewById(R.id.txt_titulo_lista)
        //texto_titulo_lista.text = intent.getStringExtra("titulo") as String

        var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

        for(Anotacion in anots){
            if(Anotacion.id_anotacion == int_id_seleccionado){
                ConexionBBDD.addTarea(this, texto_titulo_tarea.text.toString(),Anotacion.id_anotacion)

                var intentV1 = Intent(this,Ventana_lista_tareas::class.java)
                intentV1.putExtra("elegido",(Anotacion.id_anotacion).toString())
                intentV1.putExtra("titulo",Anotacion.titulo)
                startActivity(intentV1)

                finish()

            }
        }

    }


    fun volver(view: View){

        var intentV1 = Intent(this,MainActivity::class.java)
        startActivity(intentV1)

        finish()
    }



}