import kotlin.collections.HashMap
import kotlin.random.Random

class SalaEspera(var numero:Int? = null, var listaPacientes:HashMap<Int,Paciente>? = null) {

    open class Builder(var numero:Int? = null, var listaPacientes:HashMap<Int,Paciente>? = null){
        fun numero(numero:Int):Builder{
            this.numero = numero
            return this
        }
        fun listaPacientes(listaPacientes:HashMap<Int,Paciente>):Builder{
            this.listaPacientes = listaPacientes
            return this
        }
        fun build():SalaEspera{
            return SalaEspera(numero,listaPacientes)
        }
    }

    fun obtenerPaciente():Paciente?{ //saco el primer paciente de los mas prioritarios de una sala
        var pacienteTratar:Paciente? = null
        for(paci in this.listaPacientes!!.values){
            if(paci.prioridad==1){
                pacienteTratar = paci
            }
        }

        if(pacienteTratar == null){
            for(paci in this.listaPacientes!!.values){
                if(paci.prioridad==2){
                    pacienteTratar = paci
                }
            }
        }

        if(pacienteTratar == null){
            for(paci in this.listaPacientes!!.values){
                if(paci.prioridad==3){
                    pacienteTratar = paci
                }
            }
        }

        return pacienteTratar
    }

    fun liberarPaciente(nidi:Int?){
        this.listaPacientes!!.remove(nidi)
    }

    fun ingresarPaciente(p:Paciente){
        this.listaPacientes!!.put(p.nidi!!,p)
    }

    override fun toString(): String{
        return "Sala de espera "+this.numero.toString()
    }

}