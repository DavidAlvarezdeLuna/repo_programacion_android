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
import com.example.quedadas.VentanaEdicion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MiAdaptadorRecycler (var eventos : ArrayList<Evento>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>()  {

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = eventos.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return ViewHolder(layoutInflater.inflate(R.layout.item_card,parent,false))
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
        fun bind(eve: Evento, context: Context, pos: Int, miAdaptadorRecycler: MiAdaptadorRecycler){

            val db = Firebase.firestore
            val TAG = "David"

            nombreA.text = eve.nombre
            lugarA.text = eve.lugar
            fechahoraA.text = eve.fechahora


            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == MiAdaptadorRecycler.seleccionado) {
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
                    if (pos == MiAdaptadorRecycler.seleccionado){
                        MiAdaptadorRecycler.seleccionado = -1
                    }
                    else {
                        MiAdaptadorRecycler.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                    Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecycler.seleccionado.toString(), Toast.LENGTH_SHORT).show()

                    var intentV1 = Intent(context, MiMapsActivity::class.java)
                    intentV1.putExtra("lati", eve.latitud.toString())
                    intentV1.putExtra("long", eve.longitud.toString())
                    intentV1.putExtra("even",eve)
                    context.startActivity(intentV1)

                })

            itemView.setOnLongClickListener(View.OnLongClickListener
            {

                MiAdaptadorRecycler.seleccionado = pos

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogo.setTitle("¿Borrar evento?")
                dialogo.setMessage("¿Estás seguro de que quieres borrar el evento?")
                dialogo.setCancelable(false)
                dialogo.setPositiveButton("Si",
                    DialogInterface.OnClickListener { dialogo, id ->

                        db.collection("eventos").document(eve.codigo)
                            .delete()
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

                        //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.

                        miAdaptadorRecycler.eventos.removeAt(pos)

                        miAdaptadorRecycler.notifyDataSetChanged()

                    })
                dialogo.setNegativeButton("No",
                    DialogInterface.OnClickListener { dialogo, id -> "cancelar" })
                dialogo.show()

                true //me ha permitido usar los longclick si devuelven true
            })

        }
    }

}