package com.example.micolornote

import Adaptadores.MiAdaptadorVH
import Auxiliar.ConexionBBDD
import Auxiliar.Parametro
import Conexion.AdminSQLIteConexion
import Modelo.Anotacion
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.core.view.isVisible
import androidx.core.view.size
import java.util.ArrayList
import android.content.DialogInterface
import androidx.core.view.get


class MainActivity : AppCompatActivity() {

    var seleccionado:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //listview
        var lista_anots: ListView = findViewById(R.id.lst_view_anotaciones)
        var adaptador = MiAdaptadorVH(this,R.layout.tabla_anotaciones, ConexionBBDD.obtenerAnotaciones(this),seleccionado)
        lista_anots.adapter = adaptador

        var ventanaActual:MainActivity = this

        var boton_abrir:Button = findViewById(R.id.btn_abrir)
        boton_abrir.isVisible = false


        lista_anots.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                vista: View?,
                pos: Int,
                idElemnto: Long
            ) {

                if(pos == seleccionado){
                    seleccionado =-1
                    boton_abrir.isVisible = false
                }else{
                    seleccionado = pos
                    boton_abrir.isVisible = true
                }

                var adaptador = MiAdaptadorVH(ventanaActual,R.layout.tabla_anotaciones, ConexionBBDD.obtenerAnotaciones(ventanaActual),seleccionado)
                lista_anots.adapter = adaptador

            }
        }

        lista_anots.onItemLongClickListener = object: AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                parent: AdapterView<*>?,
                view: View?,
                pos: Int,
                idElemento: Long
            ): Boolean {

                seleccionado = pos
                var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this@MainActivity)

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                dialogo.setIcon(R.drawable.asistente_cancelar)
                dialogo.setTitle(R.string.ventana_titulo)
                dialogo.setMessage(R.string.ventana_mensaje)
                dialogo.setCancelable(false)
                dialogo.setPositiveButton(R.string.ventana_si,
                    DialogInterface.OnClickListener { dialogo, id ->
                        ConexionBBDD.delAnotacion(this@MainActivity, anots.get(pos).id_anotacion)
                        var intentV1 = Intent(this@MainActivity,MainActivity::class.java)
                        startActivity(intentV1)
                        finish()
                    })
                dialogo.setNegativeButton(R.string.ventana_no,
                    DialogInterface.OnClickListener { dialogo, id -> "cancelar" })
                dialogo.show()

                return true

            }
        }

    }

    fun abrir(view:View){

        var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

        if(seleccionado>=0){

            if(anots.get(seleccionado).tipo=="nota"){

                var intentV1 = Intent(this,Ventana_nota::class.java)

                intentV1.putExtra("elegido",(anots.get(seleccionado).id_anotacion).toString())
                intentV1.putExtra("titulo",anots.get(seleccionado).titulo)

                startActivity(intentV1)
                finish()

            }else{
                if(anots.get(seleccionado).tipo=="lista"){

                    var intentV1 = Intent(this,Ventana_lista_tareas::class.java)

                    intentV1.putExtra("elegido",(anots.get(seleccionado).id_anotacion).toString())
                    intentV1.putExtra("titulo",anots.get(seleccionado).titulo)

                    startActivity(intentV1)
                    finish()

                }
            }

        }

    }


    fun crear_nuevo(view:View){

        var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

        var intentV1 = Intent(this,Ventana_nuevo::class.java)
        intentV1.putExtra("siguiente", anots.size.toString())
        startActivity(intentV1)
        finish()
    }

}