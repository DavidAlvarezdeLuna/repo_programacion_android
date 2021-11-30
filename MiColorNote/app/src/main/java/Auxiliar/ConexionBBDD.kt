package Auxiliar

import Adaptadores.MiAdaptadorRecycler
import Conexion.AdminSQLIteConexion
import Modelo.Anotacion
import Modelo.Tarea
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity

object ConexionBBDD {
    var nombreBD = Parametro.nombre_bbdd

    fun cambiarBD(nombreBD:String){
        this.nombreBD = nombreBD
    }

    fun addAnotacion(contexto: AppCompatActivity, a: Anotacion){

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        val registro = ContentValues()

        registro.putNull("id_anotacion")
        registro.put("tipo",a.tipo)
        registro.put("titulo", a.titulo)
        registro.put("fecha",a.fecha)
        registro.put("hora",a.hora)
        registro.put("plantilla",a.plantilla)
        registro.put("texto_nota","")

        bd.insert("anotaciones", null, registro)

        bd.close()
    }


    fun addTarea(contexto: AppCompatActivity, titulo:String, id_anotacion: Int){

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        val registro = ContentValues()

        registro.putNull("id_tarea")
        registro.put("id_anotacion",id_anotacion)
        registro.put("texto", titulo)
        registro.put("marcado",0)
        registro.put("foto","camara")

        bd.insert("tareas", null, registro)

        bd.close()
    }

    fun delAnotacion(contexto: AppCompatActivity, id_anotacion: Int):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        //borra las tareas relacionadas con la anotacion
        bd.delete("tareas", "id_anotacion='${id_anotacion}'", null)
        val cant = bd.delete("anotaciones", "id_anotacion='${id_anotacion}'", null) //devuelve 1 si borra

        bd.close()
        return cant //0 si no borra, 1 si borra
    }

    fun delTarea(contexto: AppCompatActivity, id_tarea: Int){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        bd.delete("tareas", "id_tarea='${id_tarea}'", null)
        bd.close()
    }

    //texto, estado de tarea y foto de tarea se deben modificar por separado

    fun modTexto(contexto:AppCompatActivity, texto:String, id:Int):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("texto_nota", texto)
        val cant = bd.update("anotaciones", registro, "id_anotacion='${id}'", null)
        bd.close()
        return cant
    }


    fun modMarcado(contexto: AppCompatActivity, t:Tarea, marcado:Int, id:Int):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("marcado", marcado)
        val cant = bd.update("tareas", registro, "id_tarea='${id}'", null)
        bd.close()
        return cant
    }

    fun modFoto(contexto: AppCompatActivity, id:Int):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("foto", id.toString())
        val cant = bd.update("tareas", registro, "id_tarea='${id}'", null)
        bd.close()
        return cant
    }


    fun obtenerAnotaciones(contexto: AppCompatActivity):ArrayList<Anotacion>{
        var lista_Anotaciones:ArrayList<Anotacion> = ArrayList(0)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select id_anotacion,tipo,titulo,fecha,hora,plantilla,texto_nota from anotaciones", null)

        while (fila.moveToNext()) {

            var a:Anotacion = Anotacion(fila.getInt(0),fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(4),fila.getString(5),fila.getString(6))

            lista_Anotaciones.add(a)
        }
        bd.close()

        return lista_Anotaciones
    }


    fun obtenerTareas(contexto: AppCompatActivity, id_anotacion: Int):ArrayList<Tarea>{
        var lista_Tareas:ArrayList<Tarea> = ArrayList(0)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select id_tarea,id_anotacion,texto,marcado,foto from tareas where id_anotacion = '${id_anotacion}' ", null)

        while (fila.moveToNext()) {

            var t:Tarea = Tarea(fila.getInt(0),fila.getInt(1),fila.getString(2),fila.getInt(3),fila.getString(4))

            lista_Tareas.add(t)
        }
        bd.close()

        return lista_Tareas
    }




}