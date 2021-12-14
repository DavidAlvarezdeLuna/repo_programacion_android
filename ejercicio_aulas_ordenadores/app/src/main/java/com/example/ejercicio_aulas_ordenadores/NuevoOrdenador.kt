package com.example.ejercicio_aulas_ordenadores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.ejercicio_aulas_ordenadores.Api.ServiceBuilder
import com.example.ejercicio_aulas_ordenadores.Api.UserAPI
import com.example.ejercicio_aulas_ordenadores.Modelo.Aula
import com.example.ejercicio_aulas_ordenadores.Modelo.Ordenador
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevoOrdenador : AppCompatActivity() {

    lateinit var nuevoId : TextView
    lateinit var nuevoAula : TextView
    lateinit var nuevoModelo : TextView
    lateinit var nuevoAlmacenamiento : TextView
    lateinit var nuevoRam : TextView

    lateinit var botonAceptar : Button
    var contexto = this
    lateinit var operacion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_ordenador)

        nuevoId = findViewById(R.id.txt_id_ordenador_nuevo)
        nuevoAula = findViewById(R.id.txt_aula_nuevo)
        nuevoModelo = findViewById(R.id.txt_modelo_nuevo)
        nuevoAlmacenamiento = findViewById(R.id.txt_almacenamiento_nuevo)
        nuevoRam = findViewById(R.id.txt_ram_nuevo)

        botonAceptar = findViewById(R.id.btnAceptarNuevo)

        operacion = intent.getStringExtra("operacion").toString()
        val idBuscar = intent.getStringExtra("id").toString()
        if (operacion.equals("modificar")){
            getBuscarUnOrdenador(idBuscar)
            nuevoId.isEnabled = false  //No dejamos modificar el id_aula que es la clave del registro.
        }

    }

    fun cancelarOrdenador(view: View){
        finish()
    }

    fun aceptarOrdenador(view: View) {
        val us = Ordenador(
            nuevoId.text.toString(),
            nuevoAula.text.toString(),
            nuevoModelo.text.toString(),
            nuevoAlmacenamiento.text.toString(),
            nuevoRam.text.toString()
        )

        if (operacion.equals("nuevo")) {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.addOrdenador(us)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("Fernando", response.message())
                    Log.e("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando", "Registro efectuado con éxito.")
                        Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.e("Fernando", "Algo ha fallado en la inserción: clave duplicada.")
                        Toast.makeText(
                            contexto,
                            "Algo ha fallado en la inserción: clave duplicada",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Log.e("Fernando", "Registro efectuado con éxito.")
                        Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando", "Algo ha fallado en la conexión.")
                    Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
        //---------------- Modificar un registro -----------------
        else {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.modOrdenador(us)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("Fernando", response.message())
                    Log.e("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando", "Registro modificado con éxito.")
                        Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.e("Fernando", "Algo ha fallado en la modificación.")
                        Toast.makeText(
                            contexto,
                            "Algo ha fallado en la modificación",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Log.e("Fernando", "Registro modificado con éxito.")
                        Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando", "Algo ha fallado en la conexión.")
                    Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }

    }

    fun getBuscarUnOrdenador(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnOrdenador(idBusc);

        call.enqueue(object : Callback<Ordenador> {
            override fun onResponse(call: Call<Ordenador>, response: Response<Ordenador>) {
                val post = response.body()
                if (post != null) {
                    nuevoId.append(post.id_ordenador)
                    nuevoAula.append(post.aula)
                    nuevoModelo.append(post.modelo)
                    nuevoAlmacenamiento.append(post.almacenamiento)
                    nuevoRam.append(post.ram)
                }
                else {
                    Toast.makeText(this@NuevoOrdenador, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    botonAceptar.isEnabled = false
                }
            }
            override fun onFailure(call: Call<Ordenador>, t: Throwable) {
                Toast.makeText(this@NuevoOrdenador, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
