package Adaptadores

import Auxiliar.ConexionBBDD
import Modelo.Tarea
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import com.example.micolornote.MainActivity
import com.example.micolornote.R
import com.example.micolornote.Ventana_lista_tareas


class MiAdaptadorRecycler(var tarea : ArrayList<Tarea>, var  context: Context, var ventana: AppCompatActivity) : RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }


    /**
     * onBindViewHolder() se encarga de coger cada una de las posiciones de la lista de personajes y pasarlas a la clase
     * ViewHolder(clase interna, ver abajo) para que esta pinte todos los valores y active el evento onClick en cada uno.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tarea.get(position)
        holder.bind(item, context, position, this,ventana)
    }

    /**
     *  Como su nombre indica lo que hará será devolvernos un objeto ViewHolder al cual le pasamos la celda que hemos creado.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.tabla_tareas,parent,false))
    }

    /**
     * getItemCount() nos devuelve el tamaño de la lista, que lo necesita el RecyclerView.
     */
    override fun getItemCount(): Int {
        return tarea.size
    }


    //--------------------------------- Clase interna ViewHolder -----------------------------------
    /**
     * La clase ViewHolder. No es necesaria hacerla dentro del adapter, pero como van tan ligadas
     * se puede declarar aquí.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imagen_foto = view.findViewById(R.id.img_foto) as ImageView
        val texto_texto_tarea = view.findViewById(R.id.txt_texto_tarea) as TextView
        val imagen_marcado = view.findViewById(R.id.img_marcado) as ImageView
        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(tar: Tarea, context: Context, pos: Int, miAdaptadorRecycler: MiAdaptadorRecycler, ventana:AppCompatActivity){

            texto_texto_tarea.text = tar.texto

            val uri_foto = "@drawable/camara"
            val imageResource: Int =
                context.getResources().getIdentifier(uri_foto, null, context.getPackageName())
            var res_foto: Drawable = context.resources.getDrawable(imageResource)
            imagen_foto.setImageDrawable(res_foto)

            if (tar.marcado == 1) {
                val uri_marcado = "@drawable/cross"
                val imageResource: Int =
                    context.getResources().getIdentifier(uri_marcado, null, context.getPackageName())
                var res_marcado: Drawable = context.resources.getDrawable(imageResource)
                imagen_marcado.setImageDrawable(res_marcado)

                texto_texto_tarea.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG)

            }else{
                val uri_marcado = "@drawable/tick"
                val imageResource: Int =
                    context.getResources().getIdentifier(uri_marcado, null, context.getPackageName())
                var res_marcado: Drawable = context.resources.getDrawable(imageResource)
                imagen_marcado.setImageDrawable(res_marcado)

                texto_texto_tarea.getPaint().setFlags(0)
            }


            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            /*if (pos == MiAdaptadorRecycler.seleccionado) {
                with(texto_texto_tarea) {
                    //CAMBIAR A CUANDO ESTE

                }
            }
            else {


            }*/
            //Se levanta una escucha para cada item. Si pulsamos el seleccionado pondremos la selección a -1, en otro caso será el nuevo sleccionado.
            itemView.setOnClickListener(View.OnClickListener
            {

                MiAdaptadorRecycler.seleccionado = pos

                if(tar.marcado==1){
                    tar.marcado=0
                    texto_texto_tarea.getPaint().setFlags(0)
                    ConexionBBDD.modMarcado(ventana,tar,0,pos+1) //no me coje el this
                }else{
                    if(tar.marcado==0){
                        tar.marcado=1
                        texto_texto_tarea.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG)
                        ConexionBBDD.modMarcado(ventana,tar,1,pos+1)
                    }

                }

                //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                miAdaptadorRecycler.notifyDataSetChanged()

                Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecycler.seleccionado.toString(), Toast.LENGTH_SHORT).show()
            })

            itemView.setOnLongClickListener(View.OnLongClickListener
            {

                MiAdaptadorRecycler.seleccionado = pos

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogo.setIcon(R.drawable.asistente_cancelar)
                dialogo.setTitle("Confirmar borrado")
                dialogo.setMessage("¿Desea borrar la tarea " + tar.texto +"?")
                dialogo.setCancelable(false)
                dialogo.setPositiveButton("Si",
                    DialogInterface.OnClickListener { dialogo, id ->
                        ConexionBBDD.delTarea(ventana, pos+1)
                    })
                dialogo.setNegativeButton("No",
                    DialogInterface.OnClickListener { dialogo, id -> "cancelar" })
                dialogo.show()

                //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                miAdaptadorRecycler.notifyDataSetChanged()

                true //me ha dejado usar los longclick si devuelven true
            })

        }
    }
}

