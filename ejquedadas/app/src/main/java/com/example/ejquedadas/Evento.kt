package com.example.ejquedadas

import java.io.Serializable

data class Evento (var id:String, var nombre:String, var ubicacion:String, var fecha:String, var hora:String, var lat:Double, var long:Double, var asistentes:ArrayList<Asistente>):Serializable