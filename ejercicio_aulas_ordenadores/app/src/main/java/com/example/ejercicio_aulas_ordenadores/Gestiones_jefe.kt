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

class Gestiones_jefe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestiones_jefe)

        val fragment = MiFragment()
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: MiFragment){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_jefe, fragment)
        fragmentTransaction.commit()
    }

    //FUNCIONES PROFESORES
    fun listarTodosProfesores(view: View){
        var intentV1 = Intent(this, ListaProfesores::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }

    fun buscarPorID(view: View){
        var intentV1 = Intent(this, ListaProfesores::class.java)

        var texto_id_buscar_profesor:TextView = findViewById(R.id.txt_id_buscar_profesor)

        if (texto_id_buscar_profesor.text.trim().isEmpty()){
            Toast.makeText(this, "Rellene el campo con un DNI. Ejemplo: 1A, 2B, etc...", Toast.LENGTH_SHORT).show()
        }
        else {
            intentV1.putExtra("operacion","buscar")
            intentV1.putExtra("valorBuscar",texto_id_buscar_profesor.text.toString())
            startActivity(intentV1)
        }
    }

    fun borrarPorID(view:View){

        var texto_id_buscar_profesor:TextView = findViewById(R.id.txt_id_buscar_profesor)

        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.borrarUsuario(texto_id_buscar_profesor.text.toString())
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("Fernando",response.message())
                Log.e ("Fernando", response.code().toString())
                if (response.code() == 200) {
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@Gestiones_jefe, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.e("Fernando","Algo ha fallado en el borrado: DNI no encontrado.")
                    Toast.makeText(this@Gestiones_jefe, "Algo ha fallado en el borrado: DNI no encontrado",Toast.LENGTH_LONG).show()
                }
                if (response.isSuccessful){ //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@Gestiones_jefe, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Fernando","Algo ha fallado en la conexión.")
                Toast.makeText(this@Gestiones_jefe, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
            }
        })
    }

    fun nuevoRegistro(view:View){
        var intentV1 = Intent(this, NuevoProfesor::class.java)
        intentV1.putExtra("operacion","nuevo")
        startActivity(intentV1)
    }

    fun modificarRegistro(view:View){

        var texto_id_buscar_profesor:TextView = findViewById(R.id.txt_id_buscar_profesor)

        var intentV1 = Intent(this, NuevoProfesor::class.java)
        intentV1.putExtra("operacion","modificar")
        intentV1.putExtra("id",texto_id_buscar_profesor.text.toString())
        startActivity(intentV1)
    }

    //FUNCIONES AULA
    fun listarTodosAulas(view: View){
        var intentV1 = Intent(this, ListaAulas::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }

    fun buscarPorIDAula(view: View){
        var intentV1 = Intent(this, ListaAulas::class.java)

        var texto_id_buscar_aula:TextView = findViewById(R.id.txt_id_buscar_aula)

        if (texto_id_buscar_aula.text.trim().isEmpty()){
            Toast.makeText(this, "Rellene el campo con un ID. Ejemplo: 209, 114, etc...", Toast.LENGTH_SHORT).show()
        }
        else {
            intentV1.putExtra("operacion","buscar")
            intentV1.putExtra("valorBuscar",texto_id_buscar_aula.text.toString())
            startActivity(intentV1)
        }
    }

    fun borrarPorIDAula(view:View){

        var texto_id_buscar_aula:TextView = findViewById(R.id.txt_id_buscar_aula)

        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.borrarAula(texto_id_buscar_aula.text.toString())
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("Fernando",response.message())
                Log.e ("Fernando", response.code().toString())
                if (response.code() == 200) {
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@Gestiones_jefe, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.e("Fernando","Algo ha fallado en el borrado: DNI no encontrado.")
                    Toast.makeText(this@Gestiones_jefe, "Algo ha fallado en el borrado: DNI no encontrado",Toast.LENGTH_LONG).show()
                }
                if (response.isSuccessful){ //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@Gestiones_jefe, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Fernando","Algo ha fallado en la conexión.")
                Toast.makeText(this@Gestiones_jefe, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
            }
        })
    }

    fun nuevoRegistroAula(view:View){
        var intentV1 = Intent(this, NuevoAula::class.java)
        intentV1.putExtra("operacion","nuevo")
        startActivity(intentV1)
    }

    fun modificarRegistroAula(view:View){

        var texto_id_buscar_aula:TextView = findViewById(R.id.txt_id_buscar_aula)

        var intentV1 = Intent(this, NuevoAula::class.java)
        intentV1.putExtra("operacion","modificar")
        intentV1.putExtra("id",texto_id_buscar_aula.text.toString())
        startActivity(intentV1)
    }


    //FUNCIONES ORDENADOR
    fun listarTodosOrdenadores(view: View){
        var intentV1 = Intent(this, ListaOrdenadores::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }

    fun buscarPorIDOrdenador(view: View){
        var intentV1 = Intent(this, ListaOrdenadores::class.java)

        var texto_id_buscar_ordenador:TextView = findViewById(R.id.txt_id_buscar_ordenador)

        if (texto_id_buscar_ordenador.text.trim().isEmpty()){
            Toast.makeText(this, "Rellene el campo con un ID. Ejemplo: 209, 114, etc...", Toast.LENGTH_SHORT).show()
        }
        else {
            intentV1.putExtra("operacion","buscar")
            intentV1.putExtra("valorBuscar",texto_id_buscar_ordenador.text.toString())
            startActivity(intentV1)
        }
    }

    fun borrarPorIDOrdenador(view:View){

        var texto_id_buscar_ordenador:TextView = findViewById(R.id.txt_id_buscar_ordenador)

        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.borrarOrdenador(texto_id_buscar_ordenador.text.toString())
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("Fernando",response.message())
                Log.e ("Fernando", response.code().toString())
                if (response.code() == 200) {
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@Gestiones_jefe, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.e("Fernando","Algo ha fallado en el borrado: DNI no encontrado.")
                    Toast.makeText(this@Gestiones_jefe, "Algo ha fallado en el borrado: DNI no encontrado",Toast.LENGTH_LONG).show()
                }
                if (response.isSuccessful){ //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@Gestiones_jefe, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Fernando","Algo ha fallado en la conexión.")
                Toast.makeText(this@Gestiones_jefe, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
            }
        })
    }

    fun nuevoRegistroOrdenador(view:View){
        var intentV1 = Intent(this, NuevoOrdenador::class.java)
        intentV1.putExtra("operacion","nuevo")
        startActivity(intentV1)
    }

    fun modificarRegistroOrdenador(view:View){

        var texto_id_buscar_ordenador:TextView = findViewById(R.id.txt_id_buscar_ordenador)

        var intentV1 = Intent(this, NuevoOrdenador::class.java)
        intentV1.putExtra("operacion","modificar")
        intentV1.putExtra("id",texto_id_buscar_ordenador.text.toString())
        startActivity(intentV1)
    }

    fun volver(view:View){
        finish()
    }

}