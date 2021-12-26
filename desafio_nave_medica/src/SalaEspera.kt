import kotlin.collections.HashMap

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

    override fun toString(): String{
        return "Sala de espera "+this.numero.toString()
    }

}