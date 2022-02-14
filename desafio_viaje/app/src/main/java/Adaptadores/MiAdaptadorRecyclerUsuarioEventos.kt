package Adaptadores

import Modelo.Evento
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_viaje.MiMapsActivity
import com.example.desafio_viaje.R
import com.example.desafio_viaje.VentanaInfoEvento
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MiAdaptadorRecyclerUsuarioEventos(var eventos : ArrayList<Evento>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecyclerUsuarioEventos.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: MiAdaptadorRecyclerUsuarioEventos.ViewHolder, position: Int) {
        val item = eventos.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiAdaptadorRecyclerUsuarioEventos.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return MiAdaptadorRecyclerUsuarioEventos.ViewHolder(
            layoutInflater.inflate(
                R.layout.item_card_eventos,
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
        fun bind(eve: Evento, context: Context, pos: Int, miAdaptadorRecyclerUsuarioEventos: MiAdaptadorRecyclerUsuarioEventos){

            val db = Firebase.firestore
            val TAG = "David"

            nombreA.text = eve.nombre
            lugarA.text = eve.lugar
            fechahoraA.text = eve.fechahora


            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == MiAdaptadorRecyclerUsuarioEventos.seleccionado) {
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
                    if (pos == MiAdaptadorRecyclerUsuarioEventos.seleccionado){
                        MiAdaptadorRecyclerUsuarioEventos.seleccionado = -1
                    }
                    else {
                        MiAdaptadorRecyclerUsuarioEventos.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecyclerUsuarioEventos.notifyDataSetChanged()

                    Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecyclerUsuarioEventos.seleccionado.toString(), Toast.LENGTH_SHORT).show()

                    var intentV1 = Intent(context, VentanaInfoEvento::class.java)
                    intentV1.putExtra("even",eve)
                    context.startActivity(intentV1)
                    (context as Activity).finish()
                })

            itemView.setOnLongClickListener(View.OnLongClickListener
            {

                MiAdaptadorRecyclerUsuarioEventos.seleccionado = pos

                var intentV1 = Intent(context, MiMapsActivity::class.java)
                intentV1.putExtra("lati", eve.latitud.toString())
                intentV1.putExtra("long", eve.longitud.toString())
                intentV1.putExtra("even",eve)
                context.startActivity(intentV1)

                true
            })

        }
    }

}