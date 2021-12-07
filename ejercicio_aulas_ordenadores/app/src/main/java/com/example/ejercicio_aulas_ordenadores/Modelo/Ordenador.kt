package com.example.ejercicio_aulas_ordenadores.Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ordenador(@SerializedName("id_ordenador")
                     val id_ordenador: String? = null,

                     @SerializedName("aula")
                     val aula: String? = null,

                     @SerializedName("modelo")
                     val modelo: String? = null,

                     @SerializedName("almacenamiento")
                     val almacenamiento: String? = null,

                     @SerializedName("ram")
                     val ram: String? = null): Serializable {

}
