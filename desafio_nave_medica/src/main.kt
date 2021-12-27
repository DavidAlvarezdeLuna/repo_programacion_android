import Constantes.Constantes
import Factorias.Factoria
import kotlin.random.Random
import kotlin.collections.HashMap

fun main(){

    //Creo los medicos
    var listaMedicos: ArrayList<Medico> = ArrayList()
    for(i in 0..Constantes.NUM_MED_ESP){
        listaMedicos.add(Factoria.generarTraumatologo())
        listaMedicos.add(Factoria.generarInternista())
    }

    //Creo las salas y las añado a la nave
    for(i in 1..Constantes.NUM_SALAS){
        Nave.listaSalas.add(Factoria.generarSala(i))
    }


    //COMIENZA LA SIMULACIÓN
    var segundo:Int = 0
    var claves:Int = 0
    var numSalaSeleccionada:Int = -1
    var tratadoInterTurno:Int = 0
    var tratadoTraumaTurno:Int = 0

    do{
        println("Segundo " + segundo)

        if(segundo%2 == 0){ //cada dos segundos
            //genero un paciente nuevo
            var p:Paciente = Factoria.generarPaciente()

            //Compruebo que sala tiene menos pacientes, e ingreso alli al paciente nuevo
            numSalaSeleccionada = Nave.ingresarPaciente(p)
            println(p.toString() + " ha llegado a la nave médica y ha sido colocado en la sala "+numSalaSeleccionada.toString())
        }

        if(segundo%4 == 0){ //cada cuatro segundos
            //compruebo que sala tiene mas pacientes. Si tienen el varias coinciden, saco una al azar
            var salaSeleccionada:SalaEspera = Nave.comprobarSalaMasLlena()
            //println("size de la sala "+salaSeleccionada.numero+": "+salaSeleccionada.listaPacientes.size)

            if(salaSeleccionada.listaPacientes.size==0){
                println("Actualmente no hay pacientes a tratar!")
            }else{
                //procedo a comprobar si algun médico puede atender al paciente
                var pacienteTratar:Paciente? = salaSeleccionada.listaPacientes[0]
                println(pacienteTratar.toString())
                var medicoNecesitado:String = ""
                if(pacienteTratar?.herida == "quemadura"){
                    medicoNecesitado = "Traumatologo"
                }else{
                    if(pacienteTratar?.herida == "impacto"){
                        medicoNecesitado = "Internista"
                    }
                }

                var tratar = false
                for(med in listaMedicos){
                    if(!med.ocupado){
                        if(med.javaClass.name == medicoNecesitado){
                            for(segu in med.listaSeguros){
                                if (segu == pacienteTratar?.seguro){
                                    tratar = true
                                }
                            }
                        }
                    }

                    if(tratar){
                        println(med.toString()+" trata al "+pacienteTratar.toString())
                        med.ocupado = true
                        //salaSeleccionada.listaPacientes
                    }

                }
            }
        }

        segundo++

    }while (segundo < 210)

}