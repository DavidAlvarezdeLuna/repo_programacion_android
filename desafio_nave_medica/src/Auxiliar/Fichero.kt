package Auxiliar

import java.io.File
import java.io.FileWriter
import java.io.IOException


object Fichero {

    fun iniciarFichero(){
        val archivo = FileWriter(File("registro_medico.txt"), false)
    }

    fun escribirLinea(linea:String){
        try {
            val archivo = FileWriter(File("registro_medico.txt"), true)
            archivo.use { it.write(linea + "\r\n") }
            archivo.close()
        } catch (e: IOException) {
            println("ERROR AL INSERTAR FICHERO")
        }
    }

}