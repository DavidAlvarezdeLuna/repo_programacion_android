package Modelo

import java.io.Serializable

data class Usuario(var correo:String, var nombre:String, var apellido:String, var perfil:String, var admin:Boolean, var activado:Boolean):Serializable
