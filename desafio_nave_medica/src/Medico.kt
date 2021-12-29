import Constantes.Constantes
import kotlin.collections.ArrayList

abstract class Medico(nidi:Int? = null, nombre:String? = null, open var ocupado:Boolean? = null, open var listaSeguros:ArrayList<String>? = null):Persona(nidi,nombre) {

    /*open class Builder(var nidi:Int, var nombre:String, var ocupado:Boolean, var listaSeguros:ArrayList<String>){

        fun nidi(nidi:Int):Builder{
            this.nidi = nidi
            return this
        }
        fun nombre(nombre:String):Builder{
            this.nombre = nombre
            return this
        }
        fun ocupado(ocupado:Boolean):Builder{
            this.ocupado = ocupado
            return this
        }
        fun listaSeguros(listaSeguros:ArrayList<String>):Builder{
            this.listaSeguros = listaSeguros
            return this
        }
        fun build():Medico{
            return Medico(nidi,nombre,ocupado,listaSeguros)
        }

    }*/

    fun puedeTratar(pacienteTratar:Paciente,medicoNecesitado:String,tratadoInterTurno:Int, tratadoTraumaTurno:Int):Boolean{ //devuelve true si el medico puede tratar al paciente
        var tratar:Boolean = false

        if(!this.ocupado!!){
            if(this.javaClass.name == medicoNecesitado){
                if(this is Internista){
                    if (tratadoInterTurno < Constantes.MED_ESP_TURNO){
                        for(segu in this.listaSeguros!!) {
                            if (segu == pacienteTratar.seguro) {
                                tratar = true
                            }
                        }
                    }
                }else{
                    if(this is Traumatologo){
                        if (tratadoTraumaTurno < Constantes.MED_ESP_TURNO) {
                            for (segu in this.listaSeguros!!) {
                                if (segu == pacienteTratar.seguro) {
                                    tratar = true
                                }
                            }
                        }
                    }
                }
            }
        }

        return tratar
    }

    override fun toString(): String{
        /*var estado:String = "Disponible"
        if(this.ocupado){
            estado = "Ocupado"
        }*/
        return this.javaClass.name+" "+this.nidi+", "+this.nombre+", seguros "+this.listaSeguros
    }

}