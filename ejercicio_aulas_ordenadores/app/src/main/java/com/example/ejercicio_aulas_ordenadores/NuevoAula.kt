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
import com.example.ejercicio_aulas_ordenadores.Modelo.Profesor
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevoAula : AppCompatActivity() {

    lateinit var nuevoId : TextView
    lateinit var nuevoNombre : TextView

    lateinit var botonAceptar : Button
    var contexto = this
    lateinit var operacion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_aula)

        val fragment = MiFragment()
        replaceFragment(fragment)

        nuevoId = findViewById(R.id.txt_id_aula_nuevo)
        nuevoNombre = findViewById(R.id.txt_nombre_aula_nuevo)
        botonAceptar = findViewById(R.id.btnAceptarNuevo)

        operacion = intent.getStringExtra("operacion").toString()
        val idBuscar = intent.getStringExtra("id").toString()
        if (operacion.equals("modificar")){
            getBuscarUnAula(idBuscar)
            nuevoId.isEnabled = false  //No dejamos modificar el id_aula que es la clave del registro.
        }

    }

    private fun replaceFragment(fragment: MiFragment){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_nuevo_aula, fragment)
        fragmentTransaction.commit()
    }

    fun cancelarAula(view: View){
        finish()
    }

    fun aceptarAula(view: View) {
        val us = Aula(
            nuevoId.text.toString(),
            nuevoNombre.text.toString()
        )
        Log.e("Fernando", nuevoId.text.toString()+" "+nuevoNombre.text.toString())

        if (operacion.equals("nuevo")) {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.addAula(us)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("Fernando", response.message())
                    Log.e("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando", "Registro efectuado con ??xito.")
                        Toast.makeText(contexto, "Registro efectuado con ??xito", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.e("Fernando", "Algo ha fallado en la inserci??n: clave duplicada.")
                        Toast.makeText(
                            contexto,
                            "Algo ha fallado en la inserci??n: clave duplicada",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el c??digo.
                        Log.e("Fernando", "Registro efectuado con ??xito.")
                        Toast.makeText(contexto, "Registro efectuado con ??xito", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando", "Algo ha fallado en la conexi??n.")
                    Toast.makeText(contexto, "Algo ha fallado en la conexi??n.", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
        //---------------- Modificar un registro -----------------
        else {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.modAula(us)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("Fernando", response.message())
                    Log.e("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando", "Registro modificado con ??xito.")
                        Toast.makeText(contexto, "Registro modificado con ??xito", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.e("Fernando", "Algo ha fallado en la modificaci??n.")
                        Toast.makeText(
                            contexto,
                            "Algo ha fallado en la modificaci??n",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el c??digo.
                        Log.e("Fernando", "Registro modificado con ??xito.")
                        Toast.makeText(contexto, "Registro modificado con ??xito", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando", "Algo ha fallado en la conexi??n.")
                    Toast.makeText(contexto, "Algo ha fallado en la conexi??n.", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }

    }

    fun getBuscarUnAula(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnAula(idBusc);

        call.enqueue(object : Callback<Aula> {
            override fun onResponse(call: Call<Aula>, response: Response<Aula>) {
                val post = response.body()
                if (post != null) {
                    nuevoId.append(post.id_aula)
                    nuevoNombre.append(post.nombre_aula)
                }
                else {
                    Toast.makeText(this@NuevoAula, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    botonAceptar.isEnabled = false
                }
            }
            override fun onFailure(call: Call<Aula>, t: Throwable) {
                Toast.makeText(this@NuevoAula, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}