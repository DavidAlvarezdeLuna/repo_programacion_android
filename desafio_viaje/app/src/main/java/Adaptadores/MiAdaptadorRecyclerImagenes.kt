package Adaptadores

import Auxiliar.Login
import Modelo.Evento
import Modelo.Usuario
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafio_viaje.MiMapsActivity
import com.example.desafio_viaje.R
import com.example.desafio_viaje.VentanaInfoEvento
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class MiAdaptadorRecyclerImagenes (var imagenes : ArrayList<StorageReference>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecyclerImagenes.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: MiAdaptadorRecyclerImagenes.ViewHolder, position: Int) {
        val item = imagenes.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiAdaptadorRecyclerImagenes.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return MiAdaptadorRecyclerImagenes.ViewHolder(
            layoutInflater.inflate(
                R.layout.item_card_imagenes,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return imagenes.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagenA = view.findViewById(R.id.img_imagen) as ImageView
        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(img: StorageReference, context: Context, pos: Int, miAdaptadorRecyclerImagenes: MiAdaptadorRecyclerImagenes){

            val storage = Firebase.storage
            val storageRef = storage.reference

            val db = Firebase.firestore
            val TAG = "David"

            //var imagenRef = storageRef.child(img)

            val ONE_MEGABYTE: Long = 512 * 512
            img.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                Glide.with(context).load(it).into(imagenA)
            }.addOnFailureListener {
                // Handle any errors
            }

            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == MiAdaptadorRecyclerUsuarioEventos.seleccionado) {

            }
            //Se levanta una escucha para cada item. Si pulsamos el seleccionado pondremos la selección a -1, en otro caso será el nuevo sleccionado.
            itemView.setOnClickListener(
                View.OnClickListener
            {
                MiAdaptadorRecyclerUsuarioEventos.seleccionado = pos

                if(img.parent!!.name == Login.usu_login.correo){

                    val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                    dialogo.setTitle("¿Borrar imagen?")
                    dialogo.setMessage("¿Estás seguro de que quieres borrar la imagen?")
                    dialogo.setCancelable(false)
                    dialogo.setPositiveButton("Si",
                        DialogInterface.OnClickListener { dialogo, id ->

                            img.delete()

                            //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.

                            miAdaptadorRecyclerImagenes.notifyItemRemoved(pos)
                            miAdaptadorRecyclerImagenes.imagenes.removeAt(pos)
                            miAdaptadorRecyclerImagenes.notifyDataSetChanged()

                        })
                    dialogo.setNegativeButton("No",
                        DialogInterface.OnClickListener { dialogo, id -> "cancelar" })
                    dialogo.show()

                    true //me ha permitido usar los longclick si devuelven true

                }else{
                    Toast.makeText(context,"Foto publicada por "+img.parent!!.name,Toast.LENGTH_LONG).show()
                }

            })

        }
    }

}