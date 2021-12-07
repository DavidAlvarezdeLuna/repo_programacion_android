package com.example.ejercicio_aulas_ordenadores.Api

import com.example.ejercicio_aulas_ordenadores.Modelo.Profesor
import com.example.ejercicio_aulas_ordenadores.Modelo.Aula
import com.example.ejercicio_aulas_ordenadores.Modelo.Ordenador
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @GET("listaprofesores")
    fun getUsuarioss(): Call<MutableList<Profesor>>

    /*@GET("listado/{id}")
    fun getUnUsuario(@Path("id") id:String): Call<Usuario>

    @Headers("Content-Type:application/json")
    @POST("registrar")
    fun addUsuario(@Body info: Usuario) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUsuario(@Body info: Usuario) : Call<MutableList<Rol>>

    @DELETE("borrar/{dni}")
    fun borrarUsuario(@Path("dni") id:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("modificar")
    fun modUsuario(@Body info: Usuario) : Call<ResponseBody>*/
}