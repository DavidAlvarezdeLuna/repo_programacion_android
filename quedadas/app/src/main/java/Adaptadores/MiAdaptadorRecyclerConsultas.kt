package Adaptadores

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quedadas.Evento
import com.example.quedadas.MiMapsActivity
import com.example.quedadas.R
import com.example.quedadas.VentanaDatosAsistentes
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MiAdaptadorRecyclerConsultas (var eventos : ArrayList<Evento>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecyclerConsultas.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: MiAdaptadorRecyclerConsultas.ViewHolder, position: Int) {
        val item = eventos.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiAdaptadorRecyclerConsultas.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return MiAdaptadorRecyclerConsultas.ViewHolder(
            layoutInflater.inflate(
                R.layout.item_card,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return eventos.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreA = view.findViewById(R.id.txt_nom) as TextView
        val lugarA = view.findViewById(R.id.txt_lug) as TextView
        val fechahoraA = view.findViewById(R.id.txt_fechor) as TextView
        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(eve: Evento, context: Context, pos: Int, miAdaptadorRecyclerConsultas: MiAdaptadorRecyclerConsultas){

            val db = Firebase.firestore
            val TAG = "David"

            nombreA.text = eve.nombre
            lugarA.text = eve.lugar
            fechahoraA.text = eve.fechahora


            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == MiAdaptadorRecyclerConsultas.seleccionado) {
                with(nombreA) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(nombreA) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }
            //Se levanta una escucha para cada item. Si pulsamos el seleccionado pondremos la selección a -1, en otro caso será el nuevo sleccionado.
            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == MiAdaptadorRecyclerConsultas.seleccionado){
                        MiAdaptadorRecyclerConsultas.seleccionado = -1
                    }
                    else {
                        MiAdaptadorRecyclerConsultas.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecyclerConsultas.notifyDataSetChanged()

                    Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecyclerConsultas.seleccionado.toString(), Toast.LENGTH_SHORT).show()

                    var intentV1 = Intent(context, VentanaDatosAsistentes::class.java)
                    intentV1.putExtra("even",eve)
                    context.startActivity(intentV1)

                })

        }
    }

}