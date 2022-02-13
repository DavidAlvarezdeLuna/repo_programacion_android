package Adaptadores

import Modelo.Usuario
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_viaje.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MiAdaptadorRecyclerGestionUsuarios(var usuarios : ArrayList<Usuario>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecyclerGestionUsuarios.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: MiAdaptadorRecyclerGestionUsuarios.ViewHolder, position: Int) {
        val item = usuarios.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiAdaptadorRecyclerGestionUsuarios.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return MiAdaptadorRecyclerGestionUsuarios.ViewHolder(
            layoutInflater.inflate(
                R.layout.item_card_gestion_usuarios,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val correoU = view.findViewById(R.id.txt_correo_usuario_gestion) as TextView
        val adminU = view.findViewById(R.id.txt_admin_usuario_gestion) as TextView
        val activadoU = view.findViewById(R.id.txt_activado_usuario_gestion) as TextView

        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(usu: Usuario, context: Context, pos: Int, miAdaptadorRecyclerGestionUsuarios: MiAdaptadorRecyclerGestionUsuarios){

            val db = Firebase.firestore
            val TAG = "David"

            correoU.text = usu.correo
            adminU.text = usu.admin.toString()
            activadoU.text = usu.activado.toString()

            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == MiAdaptadorRecyclerGestionUsuarios.seleccionado) {
                with(correoU) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(correoU) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }
            //Se levanta una escucha para cada item. Si pulsamos el seleccionado pondremos la selección a -1, en otro caso será el nuevo sleccionado.
            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == MiAdaptadorRecyclerGestionUsuarios.seleccionado){
                        MiAdaptadorRecyclerGestionUsuarios.seleccionado = -1
                    }
                    else {
                        MiAdaptadorRecyclerGestionUsuarios.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecyclerGestionUsuarios.notifyDataSetChanged()

                    Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecyclerGestionUsuarios.seleccionado.toString(), Toast.LENGTH_SHORT).show()

                    val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                    dialogo.setTitle("¿Cambiar activación del usuario?")
                    dialogo.setMessage("Activado: " + usu.activado + ". ¿Estás seguro de que quieres cambiar el estado de activación del usuario?")
                    dialogo.setCancelable(false)
                    dialogo.setPositiveButton("Si",
                        DialogInterface.OnClickListener { dialogo, id ->

                            if(usu.activado){
                                db.collection("usuario").document(usu.correo)
                                    .update("activado",false)
                                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

                                //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.

                                miAdaptadorRecyclerGestionUsuarios.notifyDataSetChanged()

                            }else{
                                db.collection("usuario").document(usu.correo)
                                    .update("activado",true)
                                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

                                //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.

                                miAdaptadorRecyclerGestionUsuarios.notifyDataSetChanged()
                            }

                        })
                    dialogo.setNegativeButton("No",
                        DialogInterface.OnClickListener { dialogo, id -> "cancelar" })
                    dialogo.show()

                    true //me ha permitido usar los longclick si devuelven true

                })

        }
    }

}