package com.example.ejercicio_aulas_ordenadores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Gestiones_jefe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestiones_jefe)
    }

    fun listarTodosProfesores(view: View){
        var intentV1 = Intent(this, ListaProfesores::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }
}