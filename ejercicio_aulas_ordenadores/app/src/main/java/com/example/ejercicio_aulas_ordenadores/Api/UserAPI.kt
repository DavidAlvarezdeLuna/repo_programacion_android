package com.example.ejercicio_aulas_ordenadores.Api

import com.example.ejercicio_aulas_ordenadores.Modelo.Profesor
import com.example.ejercicio_aulas_ordenadores.Modelo.Aula
import com.example.ejercicio_aulas_ordenadores.Modelo.Ordenador
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    //FUNCIONES DE PROFESORES
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


    //FUNCIONES DE AULAS
    @GET("listaaulas")
    fun getAulass(): Call<MutableList<Aula>>

    @GET("listaaulas/{id_aula}")
    fun getUnAula(@Path("id_aula") id:String): Call<Aula>

    @Headers("Content-Type:application/json")
    @POST("registraraula")
    fun addAula(@Body info: Aula) : Call<ResponseBody>

    @DELETE("borraraula/{id_aula}")
    fun borrarAula(@Path("id_aula") id:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("modificaraula")
    fun modAula(@Body info: Aula) : Call<ResponseBody>


    //FUNCIONES DE ORDENADORES
    @GET("listaordenadores")
    fun getOrdenadoress(): Call<MutableList<Ordenador>>

    @GET("listaordenadores/{id_ordenador}")
    fun getUnOrdenador(@Path("id_ordenador") id:String): Call<Ordenador>

    @Headers("Content-Type:application/json")
    @POST("registrarordenador")
    fun addOrdenador(@Body info: Ordenador) : Call<ResponseBody>

    @DELETE("borrarordenador/{id_ordenador}")
    fun borrarOrdenador(@Path("id_ordenador") id:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("modificarordenador")
    fun modOrdenador(@Body info: Ordenador) : Call<ResponseBody>
}