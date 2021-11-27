package com.example.tomarfotosygrabarlassencillo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.graphics.BitmapFactory

import android.graphics.Bitmap




class ListaFotos : AppCompatActivity() {
    lateinit var listFots:ListView
    lateinit var imagenDetalle:ImageView
    lateinit var archivos:Array<String>
    lateinit var adaptador:ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_fotos)

        listFots = findViewById(R.id.listaFotos)
        imagenDetalle = findViewById(R.id.imgDetalle)


        var dir = getExternalFilesDir(null);
        archivos= dir!!.list();

        adaptador = ArrayAdapter(this, R.layout.item_layout,R.id.txtItem,archivos)
        listFots.adapter = adaptador

        listFots.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                val texto = listFots.getItemAtPosition(pos)
                val bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null).toString() + "/" + archivos.get(pos).toString())
                imagenDetalle.setImageBitmap(bitmap1)

                Log.e("Fernando",texto.toString())
                Toast.makeText(applicationContext,"Archivo " +  texto.toString() + " cargado.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun volver(view:View){
        finish()
    }
}