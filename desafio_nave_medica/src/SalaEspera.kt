import kotlin.collections.HashMap
import kotlin.random.Random

class SalaEspera(var numero:Int, var listaPacientes:HashMap<Int,Paciente>) {

    open class Builder(var numero:Int, var listaPacientes:HashMap<Int,Paciente>){
        fun nombre(numero:Int):Builder{
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

    fun obtenerPaciente():Paciente?{
        var listaPosiblesPrioridad:ArrayList<Paciente> = ArrayList()
        for(paci in this.listaPacientes.values){
            if(paci.prioridad==1){
                listaPosiblesPrioridad.add(paci)
            }
        }

        if(listaPosiblesPrioridad.isEmpty()){
            for(paci in this.listaPacientes.values){
                if(paci.prioridad==2){
                    listaPosiblesPrioridad.add(paci)
                }
            }
        }

        if(listaPosiblesPrioridad.isEmpty()){
            for(paci in this.listaPacientes.values){
                if(paci.prioridad==3){
                    listaPosiblesPrioridad.add(paci)
                }
            }
        }

        var pacienteTratar:Paciente? = listaPosiblesPrioridad[Random.nextInt(0,listaPosiblesPrioridad.size)]
        return pacienteTratar
    }

    override fun toString(): String{
        return "Sala de espera "+this.numero.toString()
    }

}