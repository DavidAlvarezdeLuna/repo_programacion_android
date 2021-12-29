import Constantes.Constantes
import kotlin.collections.ArrayList

abstract class Medico(nidi:Int? = null, nombre:String? = null, open var ocupado:Boolean? = null, open var listaSeguros:ArrayList<String>? = null):Persona(nidi,nombre) {

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

    fun estaOcupado(){
        this.ocupado = true
    }

    fun estaLibre(){
        this.ocupado = false
    }

    override fun toString(): String{
        return this.javaClass.name+" "+this.nidi+", "+this.nombre+", seguros "+this.listaSeguros
    }

}