package com.example.desafio_viaje

import Adaptadores.MiAdaptadorRecyclerComentarios
import Adaptadores.MiAdaptadorRecyclerGestionUsuarios
import Adaptadores.MiAdaptadorRecyclerUsuarioAniadir
import Auxiliar.Login
import Modelo.Comentario
import Modelo.Evento
import Modelo.Usuario
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class VentanaInfoEvento : AppCompatActivity() {

    private val db = Firebase.firestore
    private val TAG = "David"

    var seleccionado:Int=0

    //RecyclerView
    lateinit var recycler_comentarios : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_info_evento)

        var eve: Evento = intent.getSerializableExtra("even") as Evento

        var miAdapter = MiAdaptadorRecyclerComentarios(eve.lista_comentarios, this)
        recycler_comentarios = findViewById(R.id.recyclerview_comentarios) as RecyclerView
        recycler_comentarios.setHasFixedSize(true)
        recycler_comentarios.layoutManager = LinearLayoutManager(this)

        recycler_comentarios.adapter = miAdapter

        var texto_titulo: TextView = findViewById(R.id.txt_titulo_evento)
        texto_titulo.text = eve.nombre
    }


    fun publicarComentario(view: View){

        var eve: Evento = intent.getSerializableExtra("even") as Evento

        var texto_comentario_publicar:TextView = findViewById(R.id.txt_comentario_publicar)

        if(texto_comentario_publicar.text.trim().length>0){

            var fecha:String = Calendar.getInstance().time.date.toString()+"/"+(1+ Calendar.getInstance().time.month).toString()+"/"+(1900+ Calendar.getInstance().time.year).toString()
            var hora:String = Calendar.getInstance().time.hours.toString()+":"+ Calendar.getInstance().time.minutes.toString()
            var fechahora:String = fecha + " "+ hora

            var com:Comentario = Comentario(Login.usu_login.correo,fechahora,texto_comentario_publicar.text.toString())

            //Añado el comentario a la base de datos
            eve.lista_comentarios.add(com)

            db.collection("eventos").document(eve.codigo).update("comentarios", eve.lista_comentarios)

            texto_comentario_publicar.text = ""

        }else{
            Toast.makeText(this, "Escribe el comentario antes de publicar...", Toast.LENGTH_SHORT).show()
        }

    }

    fun abrirMapaUbicacion(view:View){

        var texto_titulo_ubicacion:TextView = findViewById(R.id.txt_titulo_ubicacion)
        var eve: Evento = intent.getSerializableExtra("even") as Evento

        if(texto_titulo_ubicacion.text.trim().length>0){
            var intentV1 = Intent(this, MapsUbicacion::class.java)
            intentV1.putExtra("titulo",texto_titulo_ubicacion.text.toString())
            intentV1.putExtra("even",eve)
            this.startActivity(intentV1)

        }else{
            Toast.makeText(this, "Escribe un titulo para el marcador...", Toast.LENGTH_SHORT).show()
        }


    }


    fun volver(view: View){
        finish()
    }

}