package com.example.ejercicio_aulas_ordenadores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_aulas_ordenadores.Adaptadores.MiAdaptadorRVAula
import com.example.ejercicio_aulas_ordenadores.Adaptadores.MiAdaptadorRVProfesor
import com.example.ejercicio_aulas_ordenadores.Api.ServiceBuilder
import com.example.ejercicio_aulas_ordenadores.Api.UserAPI
import com.example.ejercicio_aulas_ordenadores.Modelo.Aula
import com.example.ejercicio_aulas_ordenadores.Modelo.Profesor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaAulas : AppCompatActivity() {

    var aulas: ArrayList<Aula> = ArrayList()

    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_aulas)

        recyclerView = findViewById<RecyclerView>(R.id.RVListaAulas)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        val operacion = intent.getStringExtra("operacion").toString()


        if(operacion.equals("listar")){
            //getUsers()
            getAulas2()
        }
        if(operacion.equals("buscar")){
            val idBuscar = intent.getStringExtra("valorBuscar").toString()
            //getBuscarUnUsuario(idBuscar)
            getBuscarUnAula2(idBuscar)
        }

    }

    fun getAulas2() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getAulass()

        call.enqueue(object : Callback<MutableList<Aula>> {
            override fun onResponse(call: Call<MutableList<Aula>>, response: Response<MutableList<Aula>>) {
                Log.e ("Fernando", response.code().toString())
                for (post in response.body()!!) {
                    aulas.add(Aula(post.id_aula,post.nombre_aula))
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListaAulas)
                        adapter = MiAdaptadorRVAula(this@ListaAulas, aulas)
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Aula>>, t: Throwable) {
                Toast.makeText(this@ListaAulas, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getBuscarUnAula2(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnAula(idBusc);

        call.enqueue(object : Callback<Aula> {
            override fun onResponse(call: Call<Aula>, response: Response<Aula>) {
                val post = response.body()
                if (post != null) {
                    aulas.add(post)
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListaAulas)
                        adapter = MiAdaptadorRVAula(this@ListaAulas, aulas)
                    }
                }
                else {
                    Toast.makeText(this@ListaAulas, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Aula>, t: Throwable) {
                Toast.makeText(this@ListaAulas, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}