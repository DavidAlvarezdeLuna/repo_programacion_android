package Modelo

import java.io.Serializable

data class Anotacion(var id_anotacion:Int,var tipo:String,var titulo:String, var fecha:String, var hora:String, var plantilla:String, var texto_nota:String): Serializable {

}
