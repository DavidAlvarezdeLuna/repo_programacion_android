package com.example.ejercicio_aulas_ordenadores.Api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    val client = OkHttpClient.Builder().build()
    val url = "http://192.168.209.130:5000"
    /*
    Importante: 127.0.0.1 no permite ser accedido por motivos de seguridad. Sedebe poner la ip de abajo.
    Por otro lado se necesitarán en Manifes los permisos de Internet y la configuración de un xml (network_security_config.xml) que se añade como xml de actividades.
    En este XML se deben poner las ip's o dominios permitidos:
            <domain includeSubdomains="true">127.0.0.1</domain>
            <domain includeSubdomains="true">10.0.2.2</domain>
            <domain includeSubdomains="true">192.168.1.108</domain>  --> Esta será la ip de tu máquina, comprueba que tu móvil y tu equipo están en la misma red.
    e incluir el archivo en el Manifest:
     android:usesCleartextTraffic="true"  --> Esta línea también es necesaria.
     android:networkSecurityConfig="@xml/network_security_config"

     Si trabajas con tu móvil físico quizá tengas que desactivar el CORTAFUEGOS de tu portátil.
    */

    private val retrofit = Retrofit.Builder()
        .baseUrl(url) //Con 127.0.0.1 hay un problema de seguridad. Se debe acceder por esta ip (especial para enmascarar localhost) que accede a localhost.
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }

    fun buidPostService(cuerpo: RequestBody): Request {
        return Request.Builder().url(url)
            .post(cuerpo)
            .build()
    }
}