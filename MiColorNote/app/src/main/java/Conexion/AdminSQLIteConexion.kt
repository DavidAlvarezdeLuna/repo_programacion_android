package Conexion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class AdminSQLIteConexion(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table anotaciones(id_anotacion integer primary key autoincrement, tipo text, titulo text, fecha text, hora text, plantilla text, texto_nota text)")
        db.execSQL("create table tareas(id_tarea integer primary key autoincrement, id_anotacion int, texto text, marcado int, foto text)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

}