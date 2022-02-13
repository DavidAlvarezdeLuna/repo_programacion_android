package Modelo

import java.io.Serializable

data class Asistencia(var correo:String, var hora:String, var latitud:Double, var longitud:Double):Serializable
