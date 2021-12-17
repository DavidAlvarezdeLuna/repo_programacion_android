package com.example.ejercicio_aulas_ordenadores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.example.ejercicio_aulas_ordenadores.Api.ServiceBuilder
import com.example.ejercicio_aulas_ordenadores.Api.UserAPI
import com.example.ejercicio_aulas_ordenadores.Modelo.Profesor
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevoProfesor : AppCompatActivity() {
    lateinit var nuevoId : TextView
    lateinit var nuevoNombre : TextView
    lateinit var nuevoClave : TextView
    lateinit var nuevoPermisos : TextView
    lateinit var botonAceptar : Button

    lateinit var botonJefe: RadioButton
    lateinit var botonUsuario: RadioButton

    var contexto = this
    lateinit var operacion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_profesor)

        val fragment = MiFragment()
        replaceFragment(fragment)

        nuevoId = findViewById(R.id.txt_id_nuevo)
        nuevoNombre = findViewById(R.id.txt_nombre_nuevo)
        nuevoClave = findViewById(R.id.txt_clave_nuevo)
        nuevoPermisos = findViewById(R.id.txt_permisos_nuevo)
        botonAceptar = findViewById(R.id.btnAceptarNuevo)

        botonJefe = findViewById(R.id.btn_jefe)
        botonUsuario = findViewById(R.id.btn_usuario)

        operacion = intent.getStringExtra("operacion").toString()
        val idBuscar = intent.getStringExtra("id").toString()
        if (operacion.equals("modificar")){
            getBuscarUnUsuario(idBuscar)
            nuevoId.isEnabled = false  //No dejamos modificar el DNI que es la clave del registro.
        }
    }

    private fun replaceFragment(fragment: MiFragment){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_nuevo_profesor, fragment)
        fragmentTransaction.commit()
    }

    fun cancelar(view: View){
        finish()
    }

    fun aceptar(view:View) {

        var perm = "0"
        if (botonJefe.isChecked){
            perm = "1"
        }

        val us = Profesor(
            nuevoId.text.toString(),
            nuevoNombre.text.toString(),
            nuevoClave.text.toString(),
            perm

        )

        if (operacion.equals("nuevo")) {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.addUsuario(us)
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
            val call = request.modUsuario(us)
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



    fun getBuscarUnUsuario(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnUsuario(idBusc);

        call.enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                val post = response.body()
                if (post != null) {
                    nuevoId.append(post.id)
                    nuevoNombre.append(post.nombre)
                    nuevoClave.append(post.clave)
                    nuevoPermisos.append(post.permisos)
                }
                else {
                    Toast.makeText(this@NuevoProfesor, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    botonAceptar.isEnabled = false
                }
            }
            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                Toast.makeText(this@NuevoProfesor, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}