package com.example.firebasestorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    val storage = Firebase.storage
    var storageRef = storage.reference

    // Create a child reference
    // imagesRef now points to "Imagen"
    var imagesRef: StorageReference? = storageRef.child("Imagen")
    var cerezaRef = storageRef.child("Imagen/cereza.png")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun cargar(view: View){

        val ONE_MEGABYTE: Long = 1024 * 1024
        cerezaRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            Glide.with(this).load(it).into(img_cargada)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    fun descargar(view: View){
        var imagen_cargada:ImageView = findViewById(R.id.img_cargada)

        val localFile = File.createTempFile("Imagen", "png")

        cerezaRef.getFile(localFile).addOnSuccessListener {

        }.addOnFailureListener {
            // Handle any errors
        }
    }


}