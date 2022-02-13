package Adaptadores

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quedadas.Asistencia
import com.example.quedadas.Evento
import com.example.quedadas.R
import com.example.quedadas.VentanaDatosAsistentes
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MiAdaptadorRecyclerAsistentes (var asistencias : ArrayList<Asistencia>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecyclerAsistentes.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: MiAdaptadorRecyclerAsistentes.ViewHolder, position: Int) {
        val item = asistencias.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiAdaptadorRecyclerAsistentes.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return MiAdaptadorRecyclerAsistentes.ViewHolder(
            layoutInflater.inflate(
                R.layout.item_card_asistencia,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return asistencias.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val correoA = view.findViewById(R.id.txt_correo_asis) as TextView
        val fechahoraA = view.findViewById(R.id.txt_fechahora_asis) as TextView
        val latitudA = view.findViewById(R.id.txt_latitud_asis) as TextView
        val longitudA = view.findViewById(R.id.txt_longitud_asis) as TextView

        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(asi: Asistencia, context: Context, pos: Int, miAdaptadorRecyclerAsistentes: MiAdaptadorRecyclerAsistentes){

            val db = Firebase.firestore
            val TAG = "David"

            correoA.text = asi.correo
            fechahoraA.text = asi.hora
            latitudA.text = asi.latitud.toString()
            longitudA.text = asi.longitud.toString()


            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == MiAdaptadorRecyclerAsistentes.seleccionado) {
                with(correoA) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(correoA) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }
            //Se levanta una escucha para cada item. Si pulsamos el seleccionado pondremos la selección a -1, en otro caso será el nuevo sleccionado.
            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == MiAdaptadorRecyclerAsistentes.seleccionado){
                        MiAdaptadorRecyclerAsistentes.seleccionado = -1
                    }
                    else {
                        MiAdaptadorRecyclerAsistentes.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecyclerAsistentes.notifyDataSetChanged()

                    Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecyclerAsistentes.seleccionado.toString(), Toast.LENGTH_SHORT).show()
                })

        }
    }

}