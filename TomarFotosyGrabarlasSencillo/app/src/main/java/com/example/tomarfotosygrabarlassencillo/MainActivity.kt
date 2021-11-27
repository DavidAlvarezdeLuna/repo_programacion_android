package com.example.tomarfotosygrabarlassencillo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import android.os.Environment
import android.util.Log
import java.io.FileOutputStream
import java.lang.Exception


/**
 * Para este ejemplo tenemos que escribir el nombre del archivo que queremos guardar, con su extensión.
 * Para recuperar tendremos que escribir también el nombre del archivo.
 * https://tutorialesprogramacionya.com/javaya/androidya/androidstudioya/detalleconcepto.php?codigo=50&inicio=40
 */

class MainActivity : AppCompatActivity() {
    lateinit var imagen: ImageView
    lateinit var edNombre: EditText
    private val cameraRequest = 1888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)


        imagen = findViewById(R.id.imgImagen)
        edNombre = findViewById(R.id.edNombreFoto)
    }

    fun tomarFoto(view: View){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, cameraRequest)

        /*
        var intentFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var foto = File(getExternalFilesDir(null), edNombre.text.toString())
        intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto))
        startActivity(intentFoto)
         */
    }

    //https://es.stackoverflow.com/questions/33561/c%C3%B3mo-guardar-un-imageview-en-android
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            Log.e("Hola",requestCode.toString())
            if (requestCode == cameraRequest) {
                val photo: Bitmap = data?.extras?.get("data") as Bitmap
                imagen.setImageBitmap(photo)

                var fotoFichero = File(getExternalFilesDir(null), edNombre.text.toString())
                var uri = Uri.fromFile(fotoFichero)
                var fileOutStream = FileOutputStream(fotoFichero)
                photo.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
                fileOutStream.flush();
                fileOutStream.close();
            }
        }catch(e: Exception){
            Log.e("Fernando",e.toString())
        }
    }

    fun recuperarFoto(view: View){
        var bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null).toString() + "/"+edNombre.text.toString());
        imagen.setImageBitmap(bitmap1);
    }

    fun verFotos(view: View){
        val intentLista = Intent(this, ListaFotos::class.java)
        startActivity(intentLista)
    }
}