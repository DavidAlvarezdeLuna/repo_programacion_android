package com.example.micolornote

import Auxiliar.ConexionBBDD
import Modelo.Anotacion
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import java.util.*

class Ventana_nuevo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_nuevo)


        var texto_titulo_nuevo:EditText = findViewById(R.id.txt_titulo_nuevo)
        //var titulo_anotacion:String = texto_titulo_nuevo.text.toString()

        var boton_crear:Button = findViewById(R.id.btn_crear)
        var imagen_crear:ImageView = findViewById(R.id.img_crear)

        imagen_crear.setEnabled(false)

        texto_titulo_nuevo.doAfterTextChanged {

            if(texto_titulo_nuevo.text.trim().length>0){
                boton_crear.isEnabled = true
                imagen_crear.setEnabled(true)
            }else{
                boton_crear.isEnabled = false
                imagen_crear.setEnabled(false)
            }

        }
    }


    fun click_seleccionar_plantilla(view:View){

        var radio_blanco:RadioButton = findViewById(R.id.rbo_folio_blanco)
        var radio_rosa:RadioButton = findViewById(R.id.rbo_flores_rosas)
        var radio_azul:RadioButton = findViewById(R.id.rbo_flores_azules)

        var imagen_plantilla:ImageView = findViewById(R.id.img_preview_plantilla)

        if(radio_blanco.isChecked){
            imagen_plantilla.setImageResource(R.drawable.blanco)
        }else{
            if(radio_rosa.isChecked){
                imagen_plantilla.setImageResource(R.drawable.rosa)
            }else{
                if(radio_azul.isChecked) {
                    imagen_plantilla.setImageResource(R.drawable.azul)
                }
            }
        }

    }

    fun volver(view:View){

        var intentV1 = Intent(this,MainActivity::class.java)

        startActivity(intentV1)

        finish()
    }

    fun crear(view:View){

        var siguiente:String = intent.getSerializableExtra("siguiente") as String
        var siguiente_pos:Int = siguiente.toInt()

        //Recoger titulo del archivo
        var texto_titulo_nuevo:EditText = findViewById(R.id.txt_titulo_nuevo)
        var titulo_anotacion:String = texto_titulo_nuevo.text.toString()

        //Obtener si es nota o lista de tareas
        var radio_nota:RadioButton = findViewById(R.id.rbo_nota)
        var radio_lista_tareas:RadioButton = findViewById(R.id.rbo_lista_tareas)
        var tipo_anotacion:String=""

        if(radio_nota.isChecked){
            tipo_anotacion = "nota"
        }else{
            tipo_anotacion = "lista"
        }

        //Marcamos la plantilla que usara la anotacion nueva
        var radio_blanco:RadioButton = findViewById(R.id.rbo_folio_blanco)
        var radio_rosa:RadioButton = findViewById(R.id.rbo_flores_rosas)
        var radio_azul:RadioButton = findViewById(R.id.rbo_flores_azules)

        var plantilla_anotacion:String=""

        if(radio_blanco.isChecked){
            plantilla_anotacion = "blanca"
        }else{
            if(radio_rosa.isChecked){
                plantilla_anotacion = "rosa"
            }else{
                if(radio_azul.isChecked) {
                    plantilla_anotacion = "azul"
                }
            }
        }

        //sacar fecha y hora
        var fecha_anotacion:String = Calendar.getInstance().time.date.toString()+"/"+(1+Calendar.getInstance().time.month).toString()+"/"+(1900+Calendar.getInstance().time.year).toString()
        var hora_anotacion:String = Calendar.getInstance().time.hours.toString()+":"+Calendar.getInstance().time.minutes.toString()


        //con los datos recogidos, creo la Anotacion
        var a:Anotacion = Anotacion(0,tipo_anotacion,titulo_anotacion,fecha_anotacion,hora_anotacion,plantilla_anotacion,"")

        //inserto la anotacion en la base de datos
        ConexionBBDD.addAnotacion(this,a)

        //Segun lo que he creado, accedo a su pantalla de edicion
        if(radio_nota.isChecked){

            var intentV1 = Intent(this,Ventana_nota::class.java)

            var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

            var id_seleccionado:String = anots.last().id_anotacion.toString()

            intentV1.putExtra("elegido",id_seleccionado)
            intentV1.putExtra("titulo",anots.last().titulo)
            startActivity(intentV1)

            finish()

        }else{

            var intentV1 = Intent(this,Ventana_lista_tareas::class.java)

            var anots: ArrayList<Anotacion> = ConexionBBDD.obtenerAnotaciones(this)

            var id_seleccionado:String = anots.last().id_anotacion.toString()

            intentV1.putExtra("elegido",id_seleccionado)
            intentV1.putExtra("titulo",anots.last().titulo)
            startActivity(intentV1)

            finish()

        }

    }

}