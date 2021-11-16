package com.example.micolornote

import Auxiliar.ConexionBBDD
import Modelo.Anotacion
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Ventana_nota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_nota)

        var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

        var posicion:String = intent.getSerializableExtra("elegido") as String
        var int_pos:Int = posicion.toInt()-1

        var txt_plantilla:TextView = findViewById(R.id.txt_texto_nota)
        txt_plantilla.text = anots.get(int_pos).texto_nota

        if(anots.get(int_pos).plantilla == "blanca"){
            txt_plantilla.setBackgroundResource(R.drawable.blanco)
        }else{
            if(anots.get(int_pos).plantilla == "rosa") {
                txt_plantilla.setBackgroundResource(R.drawable.rosa)
            }else{
                if(anots.get(int_pos).plantilla == "azul") {
                    txt_plantilla.setBackgroundResource(R.drawable.azul)
                }
            }
        }


    }

    fun guardar_texto(view:View){

        var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

        var posicion:String = intent.getSerializableExtra("elegido") as String
        var int_pos:Int = posicion.toInt()-1

        var texto_texto_nota:EditText = findViewById(R.id.txt_texto_nota)

        ConexionBBDD.modTexto(this,texto_texto_nota.text.toString(),anots.get(int_pos).id_anotacion)

        Toast.makeText(this,"Guardado con éxito",Toast.LENGTH_LONG).show()

    }


    fun cancelar(view: View){

        var intentV1 = Intent(this,MainActivity::class.java)
        startActivity(intentV1)

        finish()
    }

}