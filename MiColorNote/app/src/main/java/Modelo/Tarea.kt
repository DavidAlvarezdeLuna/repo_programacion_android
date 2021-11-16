package Modelo

import java.io.Serializable

data class Tarea(var id_tarea:Int, var id_anotacion:Int, val texto:String, var marcado:Int, var foto:String):Serializable {
}

