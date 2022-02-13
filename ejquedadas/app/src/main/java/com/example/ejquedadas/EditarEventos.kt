package com.example.ejquedadas

import Adaptadores.MiAdaptadorRecycler
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class EditarEventos : AppCompatActivity() {

    private val db = Firebase.firestore
    private val TAG = "Daniel"
    var miArray:ArrayList<Evento> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.

    var seleccionado:Int=0
    lateinit var miRecyclerView : RecyclerView

    var miAdapter = MiAdaptadorRecycler(miArray, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_eventos)

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
        //Aquí se pondría en el setAdapter del RecyclerView.

        miRecyclerView = findViewById(R.id.rvEventos) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        //var miAdapter = MiAdaptadorRecycler(miArray, this)
        miRecyclerView.adapter = miAdapter
    }

    /**
     * Esta función accede a la colección users. Si el documento tiene roles los carga, en su defecto carga un array vacío (por coherencia de datos).
     * Dentro de esta función se rellena en el listener el arrayList que deberemos asignar al RecyclerView y, dentro del listener, cuando acabe
     * la carga de datos, notificaremos los cambios del adaptador para que cargue el RV (comentado el punto indicado de la notificación).
     */
    /*fun mostrarTodos(){
        var miArray:ArrayList<Evento> = ArrayList()
        //Coger todos los elementos de la colección.
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var roles : ArrayList<Int>

                    if (document.get("roles") != null){
                        roles = document.get("roles") as ArrayList<Int>
                    }
                    else {
                        roles = arrayListOf()
                    }
                    var al = Evento(
                        document.get("age").toString(),
                        document.get("first").toString(),
                        document.get("last").toString(),
                        roles
                    )
                    Log.e(TAG, al.toString())
                    miArray.add(al)
                }
                Log.e(TAG,miArray.toString())
                //Aquí se llamaría a: miAdaptador.notifyDataSetChanged() !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                miAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error: ", exception)
            }

        //De esta forma no se rellena el miArray porque se llega a esta línea mientras addOnSuccessListener todavía está funcionando. Los hilos y sus cosejas.
        //Esto es lo que se explicaba en el comentario anterior.
        Log.e(TAG,"----- ******  -----")
        for(e in miArray){
            Log.e(TAG,e.toString())
        }
        Log.e(TAG,"----- ******  -----")

    }*/

    //------------------------------------------------------------------------------------------
    /**
     * Este método es una función suspend que esperará a que la consulta se realiza. Será llamada
     * en un scope (entorno) de corrutinas. Hilos (ver onCreate, runBlocking).
     */
    suspend fun getDataFromFireStore()  : QuerySnapshot? {
        return try{
            val data = db.collection("eventos")
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
                var asistentes : ArrayList<Asistente>

                if (dc.document.get("asistentes") != null){
                    asistentes = dc.document.get("asistentes") as ArrayList<Asistente>
                }
                else {
                    asistentes = arrayListOf()
                }
                var al = Evento(
                    dc.document.get("id").toString(),
                    dc.document.get("nombre").toString(),
                    dc.document.get("ubicacion").toString(),
                    dc.document.get("fecha").toString(),
                    dc.document.get("hora").toString(),
                    dc.document.get("latitud").toString().toDouble(),
                    dc.document.get("longitud").toString().toDouble(),
                    asistentes
                )
                //Log.e(TAG, al.toString())
                miArray.add(al)
            }
        }
    }

    fun abrirCrearEvento(view:View){
        var intentV1 = Intent(this,CrearEvento::class.java)
        intentV1.putExtra("lat","0") //Valor por defecto
        intentV1.putExtra("long","0")
        startActivity(intentV1)
        finish()
    }

    fun volver(view: View){
        finish()
    }

}