package com.example.ejercicio_aulas_ordenadores.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_aulas_ordenadores.Modelo.Ordenador
import com.example.ejercicio_aulas_ordenadores.R

class MiAdaptadorRVOrdenador (private var context: Context,
                         private var ordenadores : ArrayList<Ordenador>
) :
    RecyclerView.Adapter<MiAdaptadorRVOrdenador.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.ordenadores_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id_ordenador.text = ordenadores[position].id_ordenador
        holder.aula.text = ordenadores[position].aula
        holder.modelo.text = ordenadores[position].modelo
        holder.almacenamiento.text = ordenadores[position].almacenamiento
        holder.ram.text = ordenadores[position].ram

        holder.itemView.setOnClickListener {
            Toast.makeText(context, ordenadores[position].id_ordenador, Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount(): Int {
        return ordenadores.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id_ordenador: TextView = itemView.findViewById<View>(R.id.txtIdOrdenador) as TextView
        var aula: TextView = itemView.findViewById<View>(R.id.txtAula) as TextView
        var modelo: TextView = itemView.findViewById<View>(R.id.txtModelo) as TextView
        var almacenamiento: TextView = itemView.findViewById<View>(R.id.txtAlmacenamiento) as TextView
        var ram: TextView = itemView.findViewById<View>(R.id.txtRam) as TextView

    }
}