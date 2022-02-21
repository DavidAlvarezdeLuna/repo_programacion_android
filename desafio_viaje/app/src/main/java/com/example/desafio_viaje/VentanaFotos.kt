package com.example.desafio_viaje

import Adaptadores.MiAdaptadorRecyclerGestionUsuarios
import Adaptadores.MiAdaptadorRecyclerImagenes
import Auxiliar.Login
import Modelo.Evento
import Modelo.Usuario
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2

class VentanaFotos : AppCompatActivity() {

    private val storage = Firebase.storage
    private val PICK_IMAGE_REQUEST:Int = 71

    private val db = Firebase.firestore
    private val TAG = "David"

    var miArray:ArrayList<Usuario> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.
    var seleccionado:Int=0

    var listaFotosStorage:ArrayList<StorageReference> = ArrayList()

    //RecyclerView
    lateinit var recycler_imagenes : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_fotos)

        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),PICK_IMAGE_REQUEST)
        }

        var eve: Evento = intent.getSerializableExtra("even") as Evento

        //CORRUTINAS
        runBlocking {
            val job : Job = launch(context = Dispatchers.Default) {
                val datos : QuerySnapshot = getDataFromFireStore() as QuerySnapshot //Obtenermos la colección
                obtenerDatos(datos as QuerySnapshot?)  //'Destripamos' la colección y la metemos en nuestro ArrayList
            }
            //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
            job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
        }

        Log.e(TAG,"----------------")
        for(e in miArray){
            Log.e(TAG,e.toString())
        }

        Log.e(TAG,"----------------")

        sacarListaFotos()

        for(pat in listaFotosStorage){
            Log.e("imag",pat.path)
        }

        //recyclerView
        var miAdapter = MiAdaptadorRecyclerImagenes(listaFotosStorage, this)

        recycler_imagenes = findViewById(R.id.recycler_fotos) as RecyclerView
        recycler_imagenes.setHasFixedSize(true)
        recycler_imagenes.layoutManager = LinearLayoutManager(this)

        recycler_imagenes.adapter = miAdapter

    }

    fun subirFoto(photo:Bitmap){
        var eve: Evento = intent.getSerializableExtra("even") as Evento
        var texto_titulo_foto:TextView = findViewById(R.id.txt_titulo_foto)

        // Create a storage reference from our app
        val storageRef = storage.reference
        val fotoRef = storageRef.child("Imagenes/"+eve.codigo+"/"+Login.usu_login.correo+"/"+texto_titulo_foto.text.toString()+".jpg")
        val fotoImagesRef = storageRef.child("Imagenes/"+eve.codigo+"/"+Login.usu_login.correo+"/"+texto_titulo_foto.text.toString()+".jpg")

        // While the file names are the same, the references point to different files
        fotoRef.name == fotoImagesRef.name // true
        fotoRef.path == fotoImagesRef.path // false

        var baos = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()
        var uploadTask = fotoRef.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(this, "Error. No se ha podido subir la imagen", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            Toast.makeText(this, "Imagen subida con exito", Toast.LENGTH_SHORT).show()
            texto_titulo_foto.text=""
        }

    }

    fun cargarFoto(view:View){
        var texto_titulo_foto:TextView = findViewById(R.id.txt_titulo_foto)

        if(texto_titulo_foto.text.trim().length>0){

            var intentV1:Intent = Intent()
            intentV1.setType("image/*")
            intentV1.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intentV1, "Select Picture"), PICK_IMAGE_REQUEST)

        }else{
            Toast.makeText(this, "Escribe el titulo antes de subir la foto", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null){
                val photo: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,data.data)
                subirFoto(photo)
            }
        }catch(e: Exception){
            Log.e("mimensaje",e.toString())
        }
    }

    suspend fun getDataFromFireStore()  : QuerySnapshot? {
        return try{
            val data = db.collection("usuario")
                //.whereEqualTo("age", 41)
                //.whereGreaterThanOrEqualTo("age",40)  //https://firebase.google.com/docs/firestore/query-data/order-limit-data?hl=es-419
                //.orderBy("age", Query.Direction.DESCENDING)
                //.limit(4) //Limita la cantidad de elementos mostrados.
                .get()
                .await()
            data
        }catch (e : Exception){
            null
        }
    }

    /**
     * Función que recuperará los datos obtenidos del método: getDataFromFireStore().
     * Llamada también desde el entorno de corrutinas: (ver onCreate, runBlocking).
     */
    private fun obtenerDatos(datos: QuerySnapshot?) {
        for(dc: DocumentChange in datos?.documentChanges!!){
            if (dc.type == DocumentChange.Type.ADDED){
                //miAr.add(dc.document.toObject(User::class.java))

                var al = Usuario(
                    dc.document.get("correo").toString(),
                    dc.document.get("nombre").toString(),
                    dc.document.get("apellido").toString(),
                    dc.document.get("perfil").toString(),
                    dc.document.get("admin").toString().toBoolean(),
                    dc.document.get("activado").toString().toBoolean()
                )
                //Log.e(TAG, al.toString())
                miArray.add(al)
            }
        }

    }

    fun sacarListaFotos(){
        var eve: Evento = intent.getSerializableExtra("even") as Evento
        var storageRef = storage.reference

        //var eventoRef = storageRef.child("Imagenes/"+eve.codigo)
        var imagesRef: StorageReference? = storageRef.child("Imagenes/"+eve.codigo)

        for (usu in miArray){
            imagesRef = storageRef.child("Imagenes/"+eve.codigo+"/"+usu.correo)
            Log.e("David",imagesRef.name.toString())

            imagesRef.listAll()
                .addOnSuccessListener { (items) ->

                    items.forEach { item ->
                        listaFotosStorage.add(item)
                        Log.e("David",item.path.toString())
                    }
                }
                .addOnFailureListener {
                    // Uh-oh, an error occurred!
                }

        }

    }

}