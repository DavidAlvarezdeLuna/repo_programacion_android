package com.example.micolornote

import Auxiliar.ConexionBBDD
import Modelo.Anotacion
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Ventana_nota : AppCompatActivity() {

    private val permissionRequest = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_nota)

        var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

        var id_seleccionado:String = intent.getSerializableExtra("elegido") as String
        var int_id_seleccionado:Int = id_seleccionado.toInt()

        var txt_plantilla:TextView = findViewById(R.id.txt_texto_nota)

        var texto_titulo_nota:TextView = findViewById(R.id.txt_titulo_nota)
        texto_titulo_nota.text = intent.getStringExtra("titulo") as String

        for(Anotacion in anots){

            if(Anotacion.id_anotacion == int_id_seleccionado){

                txt_plantilla.text = Anotacion.texto_nota

                if(Anotacion.plantilla == "blanca"){
                    txt_plantilla.setBackgroundResource(R.drawable.blanco)
                }else{
                    if(Anotacion.plantilla == "rosa") {
                        txt_plantilla.setBackgroundResource(R.drawable.rosa)
                    }else{
                        if(Anotacion.plantilla == "azul") {
                            txt_plantilla.setBackgroundResource(R.drawable.azul)
                        }
                    }
                }

            }

        }

    }

    fun guardar_texto(view:View){

        var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

        var id_seleccionado:String = intent.getSerializableExtra("elegido") as String
        var int_id_seleccionado:Int = id_seleccionado.toInt()

        var texto_texto_nota:EditText = findViewById(R.id.txt_texto_nota)

        for(Anotacion in anots) {
            if (Anotacion.id_anotacion == int_id_seleccionado) {
                ConexionBBDD.modTexto(
                    this,
                    texto_texto_nota.text.toString(),
                    Anotacion.id_anotacion
                )
                Toast.makeText(this, R.string.guardado_con_exito, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun sms(view:View){

        var texto_texto_nota:EditText = findViewById(R.id.txt_texto_nota)

        var intentV1 = Intent(this,Ventana_SMS::class.java)
        intentV1.putExtra("texto_sms",texto_texto_nota.text.toString())
        startActivity(intentV1)

    }


    fun cancelar(view: View){

        var intentV1 = Intent(this,MainActivity::class.java)
        startActivity(intentV1)

        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequest) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //miMensaje();
                //Toast.makeText(this, "Permisos concedidos...", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(this, "No tienes los permisos requeridos...", Toast.LENGTH_SHORT).show();
            }
        }
    }

}