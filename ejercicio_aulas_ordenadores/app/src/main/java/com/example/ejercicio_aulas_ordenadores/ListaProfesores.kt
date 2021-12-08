package com.example.ejercicio_aulas_ordenadores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_aulas_ordenadores.Adaptadores.MiAdaptadorRV
import com.example.ejercicio_aulas_ordenadores.Api.ServiceBuilder
import com.example.ejercicio_aulas_ordenadores.Api.UserAPI
import com.example.ejercicio_aulas_ordenadores.Modelo.Profesor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaProfesores : AppCompatActivity() {

    var profesores: ArrayList<Profesor> = ArrayList()

    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_profesores)

        recyclerView = findViewById<RecyclerView>(R.id.RVListaProfesores)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        val operacion = intent.getStringExtra("operacion").toString()


        if(operacion.equals("listar")){
            //getUsers()
            getUsers2()
        }
        if(operacion.equals("buscar")){
            val idBuscar = intent.getStringExtra("valorBuscar").toString()
            //getBuscarUnUsuario(idBuscar)
            getBuscarUnUsuario2(idBuscar)
        }
    }

    fun getUsers2() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUsuarioss()

        call.enqueue(object : Callback<MutableList<Profesor>> {
            override fun onResponse(call: Call<MutableList<Profesor>>, response: Response<MutableList<Profesor>>) {
                Log.e ("Fernando", response.code().toString())
                for (post in response.body()!!) {
                    profesores.add(Profesor(post.id,post.nombre,post.clave,post.permisos))
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListaProfesores)
                        adapter = MiAdaptadorRV(this@ListaProfesores, profesores)
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Profesor>>, t: Throwable) {
                Toast.makeText(this@ListaProfesores, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getBuscarUnUsuario2(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnUsuario(idBusc);

        call.enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                val post = response.body()
                if (post != null) {
                    profesores.add(post)
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListaProfesores)
                        adapter = MiAdaptadorRV(this@ListaProfesores, profesores)
                    }
                }
                else {
                    Toast.makeText(this@ListaProfesores, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                Toast.makeText(this@ListaProfesores, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}