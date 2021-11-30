package Adaptadores

import Modelo.Anotacion
import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.ColorInt
import com.example.micolornote.R

class MiAdaptadorVH : ArrayAdapter<Anotacion> {

    private var context: Activity
    private var resource: Int
    private var valores: ArrayList<Anotacion>? = null
    private var seleccionado:Int = 0

    constructor(context: Activity, resource: Int, valores: ArrayList<Anotacion>, seleccionado:Int) : super(context, resource) {
        this.context = context
        this.resource = resource
        this.valores = valores
        this.seleccionado = seleccionado
    }

    override fun getCount(): Int {
        return this.valores?.size!!
    }

    override fun getItem(position: Int): Anotacion? {
        return this.valores?.get(position)
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View? = convertView
        var holder = ViewHolder()
        if (view == null) {
            if (this.context!=null) {
                view = context.layoutInflater.inflate(this.resource, null)
                holder.texto_titulo_anotacion = view.findViewById(R.id.txt_titulo_anotacion)
                holder.texto_fecha_anotacion = view.findViewById(R.id.txt_fecha_anotacion)
                holder.texto_hora_anotacion = view.findViewById(R.id.txt_hora_anotacion)
                view.tag = holder
            }
        }
        else {
            holder = view?.tag as ViewHolder
        }
        var valor: Anotacion = this.valores!![position]
        holder.texto_titulo_anotacion?.text = valor.titulo
        holder.texto_fecha_anotacion?.text = valor.fecha
        holder.texto_hora_anotacion?.text = valor.hora


        if (valor.tipo == "nota") {
            holder.texto_titulo_anotacion?.setBackgroundColor(Color.WHITE)
            holder.texto_fecha_anotacion?.setBackgroundColor(Color.WHITE)
            holder.texto_hora_anotacion?.setBackgroundColor(Color.WHITE)
        }else{
            holder.texto_titulo_anotacion?.setBackgroundColor(Color.CYAN)
            holder.texto_fecha_anotacion?.setBackgroundColor(Color.CYAN)
            holder.texto_hora_anotacion?.setBackgroundColor(Color.CYAN)
        }

        if(position == seleccionado){
            holder.texto_titulo_anotacion?.setBackgroundResource(R.color.color_botones)
            holder.texto_fecha_anotacion?.setBackgroundResource(R.color.color_botones)
            holder.texto_hora_anotacion?.setBackgroundResource(R.color.color_botones)
        }

        return view!!
    }

    class ViewHolder(){
        var texto_titulo_anotacion:TextView? = null
        var texto_fecha_anotacion:TextView? = null
        var texto_hora_anotacion:TextView? = null
    }
}