package com.example.ejercicio_aulas_ordenadores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_aulas_ordenadores.Adaptadores.MiAdaptadorRVAula
import com.example.ejercicio_aulas_ordenadores.Adaptadores.MiAdaptadorRVOrdenador
import com.example.ejercicio_aulas_ordenadores.Adaptadores.MiAdaptadorRVProfesor
import com.example.ejercicio_aulas_ordenadores.Api.ServiceBuilder
import com.example.ejercicio_aulas_ordenadores.Api.UserAPI
import com.example.ejercicio_aulas_ordenadores.Modelo.Aula
import com.example.ejercicio_aulas_ordenadores.Modelo.Ordenador
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaOrdenadores : AppCompatActivity() {

    var ordenadores: ArrayList<Ordenador> = ArrayList()

    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_ordenadores)

        recyclerView = findViewById<RecyclerView>(R.id.RVListaOrdenadores)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        val operacion = intent.getStringExtra("operacion").toString()


        if(operacion.equals("listar")){
            //getUsers()
            getOrdenadores2()
        }
        if(operacion.equals("buscar")){
            val idBuscar = intent.getStringExtra("valorBuscar").toString()
            //getBuscarUnUsuario(idBuscar)
            getBuscarUnOrdenador2(idBuscar)
        }

    }

    fun getOrdenadores2() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getOrdenadoress()

        call.enqueue(object : Callback<MutableList<Ordenador>> {
            override fun onResponse(call: Call<MutableList<Ordenador>>, response: Response<MutableList<Ordenador>>) {
                Log.e ("Fernando", response.code().toString())
                for (post in response.body()!!) {
                    ordenadores.add(Ordenador(post.id_ordenador,post.aula,post.modelo,post.almacenamiento,post.ram))
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListaOrdenadores)
                        adapter = MiAdaptadorRVOrdenador(this@ListaOrdenadores, ordenadores)
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Ordenador>>, t: Throwable) {
                Toast.makeText(this@ListaOrdenadores, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getBuscarUnOrdenador2(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnOrdenador(idBusc);

        call.enqueue(object : Callback<Ordenador> {
            override fun onResponse(call: Call<Ordenador>, response: Response<Ordenador>) {
                val post = response.body()
                if (post != null) {
                    ordenadores.add(post)
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListaOrdenadores)
                        adapter = MiAdaptadorRVOrdenador(this@ListaOrdenadores, ordenadores)
                    }
                }
                else {
                    Toast.makeText(this@ListaOrdenadores, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Ordenador>, t: Throwable) {
                Toast.makeText(this@ListaOrdenadores, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}