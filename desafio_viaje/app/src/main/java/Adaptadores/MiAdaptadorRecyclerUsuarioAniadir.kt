package Adaptadores

import Auxiliar.Login
import Modelo.Asistencia
import Modelo.Evento
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
import java.util.*
import kotlin.collections.ArrayList

class MiAdaptadorRecyclerUsuarioAniadir (var usuarios : ArrayList<Usuario>, var  context: Context, var eve: Evento) : RecyclerView.Adapter<MiAdaptadorRecyclerUsuarioAniadir.ViewHolder>()  {

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: MiAdaptadorRecyclerUsuarioAniadir.ViewHolder, position: Int) {
        val item = usuarios.get(position)
        holder.bind(item, context, position, this, eve)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiAdaptadorRecyclerUsuarioAniadir.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return MiAdaptadorRecyclerUsuarioAniadir.ViewHolder(
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
        fun bind(usu: Usuario, context: Context, pos: Int, miAdaptadorRecyclerUsuarioAniadir: MiAdaptadorRecyclerUsuarioAniadir, eve:Evento){

            val db = Firebase.firestore
            val TAG = "David"

            correoU.text = usu.correo
            adminU.text = usu.admin.toString()
            activadoU.text = usu.activado.toString()

            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == MiAdaptadorRecyclerUsuarioAniadir.seleccionado) {
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
                    if (pos == MiAdaptadorRecyclerUsuarioAniadir.seleccionado){
                        MiAdaptadorRecyclerUsuarioAniadir.seleccionado = -1
                    }
                    else {
                        MiAdaptadorRecyclerUsuarioAniadir.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecyclerUsuarioAniadir.notifyDataSetChanged()

                    Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecyclerUsuarioAniadir.seleccionado.toString(), Toast.LENGTH_SHORT).show()

                    val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                    dialogo.setTitle("Invitar al evento")
                    dialogo.setMessage("¿Quieres invitar al usuario?")
                    dialogo.setCancelable(false)
                    dialogo.setPositiveButton("Si",
                        DialogInterface.OnClickListener { dialogo, id ->

                            var esta_invitado:Boolean = false

                            for(asist in eve.lista_asistencias){
                                if(asist.correo == usu.correo){
                                    esta_invitado = true
                                }
                            }

                            if(esta_invitado){
                                Toast.makeText(context,"El usuario ya estaba invitado",Toast.LENGTH_LONG).show()
                            }else{

                                var fecha_asi:String = "Invitado"
                                var hora_asi:String = ""
                                var fecha_hora_asi:String = fecha_asi + " "+ hora_asi

                                var asi: Asistencia = Asistencia(usu.correo,fecha_hora_asi,"0".toDouble(), "0".toDouble())

                                eve.lista_asistencias.add(asi)

                                db.collection("eventos").document(eve.codigo).update("asistencias", eve.lista_asistencias)


                                miAdaptadorRecyclerUsuarioAniadir.notifyDataSetChanged()
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