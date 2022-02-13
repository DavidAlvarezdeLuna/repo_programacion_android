package com.example.ejquedadas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_crear_evento.*
import java.util.*

class CrearEvento : AppCompatActivity() {

    //En esta ventana va la creacion y adicion de eventos a firestore

    private val db = Firebase.firestore
    private val TAG = "Daniel"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_evento)

        var txtLat:TextView = findViewById(R.id.txtLatNuevo)
        var txtLong:TextView = findViewById(R.id.txtLongNuevo)

        txtLat.text = intent.getSerializableExtra("lat") as String
        txtLong.text = intent.getSerializableExtra("long") as String
    }

    fun crearNuevoEvento(view:View){
        var txtId:TextView = findViewById(R.id.txtId)
        var txtNomb:TextView = findViewById(R.id.txtNomNuevo)
        var txtUbi:TextView = findViewById(R.id.txtUbiNuevo)
        var txtFec:TextView = findViewById(R.id.txtFecNuevo)
        var txtHora:TextView = findViewById(R.id.txtHoraNuevo)
        var txtLat:TextView = findViewById(R.id.txtLatNuevo)
        var txtLong:TextView = findViewById(R.id.txtLongNuevo)


        var evento = hashMapOf(
            "id" to txtId.text.toString(),
            "nombre" to txtNomb.text.toString(),
            "ubicacion" to txtUbi.text.toString(),
            "fecha" to txtFec.text.toString(),
            "hora" to txtHora.text.toString(),
            "latitud" to txtLat.text.toString().toDouble(),
            "longitud" to txtLong.text.toString().toDouble(),
            "asistentes" to ArrayList<Asistente>()
        )

        //Añade el evento nuevo
        db.collection("eventos")
            .document(txtId.text.toString())  //Si hubiera un campo duplicado, lo reemplaza.
            .set(evento)
            .addOnSuccessListener {
                Log.e(TAG, "Evento añadido.")
                Log.e(TAG,"2")

                txtId.text=""
                txtNomb.text=""
                txtUbi.text=""
                txtFec.text=""
                txtHora.text=""
                txtLat.text=""
                txtLong.text=""

            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error al añadir el documento", e.cause)
            }

    }

    fun seleccionarCordenadas(view: View){
        var intentV1 = Intent(this,MapsCrearEvento::class.java)
        startActivity(intentV1)

    }

    fun volver(view:View){
        var intentV1 = Intent(this,EditarEventos::class.java)
        startActivity(intentV1)
        finish()
    }

}