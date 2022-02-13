package Modelo

import java.io.Serializable

data class Evento(var codigo:String, var nombre:String, var lugar:String, var fechahora:String, var latitud:Double, var longitud:Double, var lista_asistencias:ArrayList<Asistencia>): Serializable