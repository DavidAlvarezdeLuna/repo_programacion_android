package Adaptadores

import Modelo.Asistencia
import Modelo.Comentario
import Modelo.Evento
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_viaje.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MiAdaptadorRecyclerComentarios (var comentarios : ArrayList<Comentario>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecyclerComentarios.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: MiAdaptadorRecyclerComentarios.ViewHolder, position: Int) {
        val item = comentarios.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiAdaptadorRecyclerComentarios.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return MiAdaptadorRecyclerComentarios.ViewHolder(
            layoutInflater.inflate(
                R.layout.item_card_comentarios,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return comentarios.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val correoC = view.findViewById(R.id.txt_comentario_correo) as TextView
        val fechahoraC = view.findViewById(R.id.txt_comentario_fechahora) as TextView
        val textoC = view.findViewById(R.id.txt_comentario_texto) as TextView

        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(comen: Comentario, context: Context, pos: Int, miAdaptadorRecyclerComentarios: MiAdaptadorRecyclerComentarios){

            val db = Firebase.firestore
            val TAG = "David"

            correoC.text = comen.correo.toString()
            fechahoraC.text = comen.fechahora.toString()
            textoC.text = comen.texto.toString()

            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == MiAdaptadorRecyclerComentarios.seleccionado) {
                with(correoC) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(correoC) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }

        }
    }

}