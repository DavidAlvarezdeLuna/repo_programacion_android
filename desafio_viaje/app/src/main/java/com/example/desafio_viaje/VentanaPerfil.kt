package com.example.desafio_viaje

import Adaptadores.MiAdaptadorRecyclerAsistentes
import Auxiliar.Login
import Modelo.Usuario
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class VentanaPerfil : AppCompatActivity() {

    private val db = Firebase.firestore
    private val TAG = "David"

    var miArray:ArrayList<Usuario> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.
    var seleccionado:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_perfil)

        //CORRUTINAS
        runBlocking {
            val job : Job = launch(context = Dispatchers.Default) {
                val datos : QuerySnapshot = getDataFromFireStore() as QuerySnapshot //Obtenermos la colección
                obtenerDatos(datos as QuerySnapshot?)  //'Destripamos' la colección y la metemos en nuestro ArrayList
            }
            //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
            job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
        }

        var txtCorreo: TextView = findViewById(R.id.txt_perfil_correo)
        var txtNombre: TextView = findViewById(R.id.txt_perfil_nom)
        var txtApellido: TextView = findViewById(R.id.txt_perfil_ape)
        var txtPerfil: TextView = findViewById(R.id.txt_perfil_per)
        var botonCambiarRol: Button = findViewById(R.id.btn_cambiar_rol)

        txtCorreo.text = Login.usu_login.correo
        txtNombre.text = Login.usu_login.nombre
        txtApellido.text = Login.usu_login.apellido
        txtPerfil.text = Login.usu_login.perfil

        if(Login.usu_login.admin){
            botonCambiarRol.isVisible = true
        }

    }

    fun guardarPerfil(view:View){

        /*eve.lista_asistencias.removeAt(pos)
        db.collection("eventos").document(eve.codigo).update("asistencias", eve.lista_asistencias)
        miAdaptadorRecyclerAsistentes.notifyDataSetChanged()*/

        var txtNombre: TextView = findViewById(R.id.txt_perfil_nom)
        var txtApellido: TextView = findViewById(R.id.txt_perfil_ape)
        var txtPerfil: TextView = findViewById(R.id.txt_perfil_per)

        if(txtNombre.text.trim().isNotEmpty()){
            db.collection("usuario").document(Login.usu_login.correo).update("nombre", txtNombre.text.toString())
            Login.usu_login.nombre = txtNombre.text.toString()
        }

        if(txtApellido.text.trim().isNotEmpty()){
            db.collection("usuario").document(Login.usu_login.correo).update("apellido", txtApellido.text.toString())
            Login.usu_login.apellido = txtApellido.text.toString()
        }

        if(txtPerfil.text.trim().isNotEmpty()){
            db.collection("usuario").document(Login.usu_login.correo).update("perfil", txtPerfil.text.toString())
            Login.usu_login.perfil = txtPerfil.text.toString()
        }

        finish()

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

    fun cambiarAUsuario(view:View){

        var cont_admin = 0

        for(usu in miArray){
            if(usu.admin){
                cont_admin++
            }
        }

        if(cont_admin < 2){
            Toast.makeText(this, "No puedes cambiar a usuario porque el sistema se quedaria sin administradores", Toast.LENGTH_SHORT).show()
        }else{
            val dialogo: AlertDialog.Builder = AlertDialog.Builder(this)
            dialogo.setTitle("ALERTA: CAMBIO IRREVERSIBLE")
            dialogo.setMessage("¿Estás seguro de que quieres cambiar tu rol a usuario?")
            dialogo.setCancelable(false)
            dialogo.setPositiveButton("Si",
                DialogInterface.OnClickListener { dialogo, id ->
                    db.collection("usuario").document(Login.usu_login.correo).update("admin", false)
                    Login.usu_login.admin = false

                    var intentV1 = Intent(this, MainActivity::class.java)
                    startActivity(intentV1)
                    finish()

                    finish()
                })
            dialogo.setNegativeButton("No",
                DialogInterface.OnClickListener { dialogo, id -> "cancelar" })
            dialogo.show()
        }

    }

    fun volver(view: View){
        finish()
    }

}