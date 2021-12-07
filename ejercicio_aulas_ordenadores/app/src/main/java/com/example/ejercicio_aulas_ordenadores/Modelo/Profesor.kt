package com.example.ejercicio_aulas_ordenadores.Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profesor(@SerializedName("id")
                    val id: String? = null,

                    @SerializedName("nombre")
                    val nombre: String? = null,

                    @SerializedName("clave")
                    val clave: String? = null,

                    @SerializedName("permisos")
                    val permisos: String? = null): Serializable{

                    }
