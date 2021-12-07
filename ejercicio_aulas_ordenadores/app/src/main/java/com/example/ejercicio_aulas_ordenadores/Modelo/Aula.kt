package com.example.ejercicio_aulas_ordenadores.Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Aula(@SerializedName("id_aula")
                val id_aula: String? = null,

                @SerializedName("nombre_aula")
                val nombre_aula: String? = null): Serializable {

}
