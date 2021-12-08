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

    @GET("listaprofesores/{id}")
    fun getUnUsuario(@Path("id") id:String): Call<Profesor>

    @Headers("Content-Type:application/json")
    @POST("registrarprofesor")
    fun addUsuario(@Body info: Profesor) : Call<ResponseBody>

    /*@Headers("Content-Type:application/json")
    @POST("login")
    fun loginUsuario(@Body info: Usuario) : Call<MutableList<Rol>>*/

    @DELETE("borrarprofesor/{id}")
    fun borrarUsuario(@Path("id") id:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("modificarprofesor")
    fun modUsuario(@Body info: Profesor) : Call<ResponseBody>
}