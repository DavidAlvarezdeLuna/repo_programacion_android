package Modelo

import java.io.Serializable

data class Contacto(var id:String, var nombre:String, var lista_numeros:ArrayList<String>?):Serializable{

}
