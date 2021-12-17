package com.example.ejercicio_aulas_ordenadores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.ejercicio_aulas_ordenadores.Api.ServiceBuilder
import com.example.ejercicio_aulas_ordenadores.Api.UserAPI
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Gestiones_usuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestiones_usuario)

        val fragment = MiFragment()
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: MiFragment){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutUsuario, fragment)
        fragmentTransaction.commit()
    }

    //FUNCIONES PROFESORES
    fun listarTodosProfesores(view: View){
        var intentV1 = Intent(this, ListaProfesores::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }



    //FUNCIONES AULA
    fun listarTodosAulas(view: View){
        var intentV1 = Intent(this, ListaAulas::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }


    //FUNCIONES ORDENADOR
    fun listarTodosOrdenadores(view: View){
        var intentV1 = Intent(this, ListaOrdenadores::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }


    fun volver(view:View){
        finish()
    }

}