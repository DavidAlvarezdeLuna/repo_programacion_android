import Constantes.Constantes
import kotlin.collections.ArrayList

abstract class Medico(nidi:Int? = null, nombre:String? = null, open var listaSeguros:ArrayList<String>? = null):Persona(nidi,nombre) {

    fun puedeTratar(pacienteTratar:Paciente,medicoNecesitado:String):Boolean{ //devuelve true si el medico puede tratar al paciente
        var tratar:Boolean = false

        if(this.javaClass.name == medicoNecesitado){
            if(this is Internista){
                for(segu in this.listaSeguros!!) {
                    if (segu == pacienteTratar.seguro) {
                        tratar = true
                    }
                }
            }else{
                if(this is Traumatologo){
                    for (segu in this.listaSeguros!!) {
                        if (segu == pacienteTratar.seguro) {
                            tratar = true
                        }
                    }
                }
            }
        }

        return tratar
    }

    override fun toString(): String{
        return this.javaClass.name+" "+this.nidi+", "+this.nombre+", seguros "+this.listaSeguros
    }

}