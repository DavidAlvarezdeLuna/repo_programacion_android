package com.example.desafio_viaje

import Modelo.Asistencia
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class VentanaEventoNuevo : AppCompatActivity() {

    private val db = Firebase.firestore
    private val TAG = "David"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_evento_nuevo)
    }

    override fun onResume() {
        super.onResume()

        var lati_nuevo:String = intent.getSerializableExtra("lati") as String
        var long_nuevo:String = intent.getSerializableExtra("long") as String

        var txtLatitudC: TextView = findViewById(R.id.txt_latitud_evento)
        var txtLongitudC: TextView = findViewById(R.id.txt_longitud_evento)

        txtLatitudC.text = lati_nuevo
        txtLongitudC.text = long_nuevo
    }

    fun irMapa(view: View){
        var intentV1 = Intent(this,MapsCreacion::class.java)
        startActivity(intentV1)
    }

    fun cancelarCrear(view: View){
        var intentV1 = Intent(this,VentanaGestionEventos::class.java)
        startActivity(intentV1)
        finish()
    }

    fun crearEvento(view: View){
        var txt_tituloC: TextView = findViewById(R.id.txt_tituloquedada)
        var txtNombreC: TextView = findViewById(R.id.txt_nombre_evento)
        var txtLugarC: TextView = findViewById(R.id.txt_lugar_evento)
        var txtFechahoraC: TextView = findViewById(R.id.txt_fechahora_evento)
        var txtLatitudC: TextView = findViewById(R.id.txt_latitud_evento)
        var txtLongitudC: TextView = findViewById(R.id.txt_longitud_evento)

        if(txtNombreC.text.length == 0 || txtLugarC.text.length == 0 || txtFechahoraC.text.length == 0 || txt_tituloC.text.length == 0 || txtLatitudC.text.length == 0 || txtLongitudC.text.length == 0){
            Toast.makeText(this, "Introduce datos en todos los campos", Toast.LENGTH_SHORT).show()
        }else{
            var evento = hashMapOf(
                "codigo" to txt_tituloC.text.toString(),
                "nombre" to txtNombreC.text.toString(),
                "lugar" to txtLugarC.text.toString(),
                "fechahora" to txtFechahoraC.text.toString(),
                "latitud" to txtLatitudC.text.toString(),
                "longitud" to txtLongitudC.text.toString(),
                "asistencias" to ArrayList<Asistencia>()
            )

            // Add a new document with a generated ID
            db.collection("eventos")
                .document(txt_tituloC.text.toString())  //Si hubiera un campo duplicado, lo reemplaza.
                .set(evento)
                .addOnSuccessListener {
                    Log.e(TAG, "Evento añadido")
                    Toast.makeText(this, "Evento añadido", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error: No se ha podido añadir", e.cause)
                    Toast.makeText(this, "Error: No se ha podido añadir", Toast.LENGTH_SHORT).show()
                }
        }


    }
}