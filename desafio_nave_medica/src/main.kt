import Constantes.Constantes
import Factorias.Factoria
import kotlin.random.Random
import kotlin.collections.HashMap
import Auxiliar.Fichero

fun main(){

    //Creo el fichero
    Fichero.iniciarFichero()

    var listaDias:Array<String> = arrayOf("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo")
    var listaTurnos:Array<String> = arrayOf("Primer turno","Segundo turno","Tercer turno")

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
    var numSalaSeleccionada:Int = -1
    var tratadoInterTurno:Int = 0
    var tratadoTraumaTurno:Int = 0

    var dia:String = listaDias[segundo/Constantes.DURACION_DIA]
    var turno:String = listaTurnos[(segundo%Constantes.DURACION_DIA)/Constantes.DURACION_TURNO]
    var momento:String = dia+", "+turno

    do{
        //println("Segundo " + segundo)
        //Fichero.escribirLinea("Segundo " + segundo)

        if(segundo%2 == 0){ //cada dos segundos
            //genero un paciente nuevo
            var p:Paciente = Factoria.generarPaciente()

            //Compruebo que sala tiene menos pacientes, e ingreso alli al paciente nuevo
            numSalaSeleccionada = Nave.ingresarPaciente(p)
            println(momento+": "+p.toString() + " ha llegado a la nave médica con herida causada por "+p.herida+" y ha sido colocado en la sala "+numSalaSeleccionada.toString())
            Fichero.escribirLinea(momento+": "+p.toString() + " ha llegado a la nave médica con herida causada por "+p.herida+" y ha sido colocado en la sala "+numSalaSeleccionada.toString())
        }

        if(segundo%4 == 0){ //cada cuatro segundos
            //compruebo que sala tiene mas pacientes. Si tienen el varias coinciden, saco una al azar
            var salaSeleccionada:SalaEspera = Nave.comprobarSalaMasLlena()
            //println("size de la sala "+salaSeleccionada.numero+": "+salaSeleccionada.listaPacientes.size)
            println("SALA SELECCIONADA: "+salaSeleccionada.numero)
            if(salaSeleccionada.listaPacientes.isEmpty()){
                println(momento+": "+"Actualmente no hay pacientes a tratar!")
                Fichero.escribirLinea(momento+": "+"Actualmente no hay pacientes a tratar!")
            }else{
                //saco el paciente y procedo a comprobar si algun médico puede atender al paciente

                var listaPosiblesPrioridad:ArrayList<Paciente> = ArrayList()
                for(paci in salaSeleccionada.listaPacientes.values){
                    if(paci.prioridad==1){
                        listaPosiblesPrioridad.add(paci)
                    }
                }

                if(listaPosiblesPrioridad.isEmpty()){
                    for(paci in salaSeleccionada.listaPacientes.values){
                        if(paci.prioridad==2){
                            listaPosiblesPrioridad.add(paci)
                        }
                    }
                }

                if(listaPosiblesPrioridad.isEmpty()){
                    for(paci in salaSeleccionada.listaPacientes.values){
                        if(paci.prioridad==3){
                            listaPosiblesPrioridad.add(paci)
                        }
                    }
                }

                var pacienteTratar:Paciente? = listaPosiblesPrioridad[Random.nextInt(0,listaPosiblesPrioridad.size)]
                println(pacienteTratar.toString())
                var medicoNecesitado:String = ""
                if(pacienteTratar?.herida.equals("quemadura")){
                    medicoNecesitado = "Traumatologo"
                }else{
                    if(pacienteTratar?.herida.equals("impacto")){
                        medicoNecesitado = "Internista"
                    }
                }

                var tratar = false
                for(med in listaMedicos){
                    if(!med.ocupado){
                        if(med.javaClass.name == medicoNecesitado){
                            if(med is Internista){
                                println("elintern")
                                if (tratadoInterTurno < Constantes.MED_ESP_TURNO){
                                    println("constante ok")
                                    for(segu in med.listaSeguros) {
                                        if (segu == pacienteTratar?.seguro) {
                                            println("seguro ok")
                                            tratar = true
                                            tratadoInterTurno++
                                        }
                                    }
                                }
                            }else{
                                if(med is Traumatologo){
                                    println("eltrauma")
                                    if (tratadoTraumaTurno < Constantes.MED_ESP_TURNO) {
                                        println("constante ok")
                                        for (segu in med.listaSeguros) {
                                            if (segu == pacienteTratar?.seguro) {
                                                println("seguro ok")
                                                tratar = true
                                                tratadoTraumaTurno++
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if(tratar){
                        println("SEGUROS "+pacienteTratar?.seguro+" y "+med.listaSeguros)
                        println(momento+": "+med.toString()+" trata al "+pacienteTratar.toString()+" de su herida causada por "+pacienteTratar?.herida)
                        Fichero.escribirLinea(momento+": "+med.toString()+" trata al "+pacienteTratar.toString()+" de su herida causada por "+pacienteTratar?.herida)
                        med.ocupado = true
                        salaSeleccionada.listaPacientes.remove(pacienteTratar?.nidi)
                        tratar = false
                    }
                }

                if(!tratar){
                    //derivo al paciente a otra nave
                    salaSeleccionada.listaPacientes.remove(pacienteTratar?.nidi)
                    println(momento+": "+pacienteTratar.toString()+" ha sido derivado a otra nave hospital para realizar su tratamiento")
                    Fichero.escribirLinea(momento+": "+pacienteTratar.toString()+" ha sido dervado a otra nave hospital para realizar su tratamiento")
                }
            }
        }

        segundo++

        if(segundo%Constantes.DURACION_TURNO == 0){ //10 segundos
            //cambio de turno. Reseteo las condiciones necesarias
            if(segundo != Constantes.DURACION_DIA*Constantes.DIAS){

                dia = listaDias[segundo/Constantes.DURACION_DIA]
                turno = listaTurnos[(segundo%Constantes.DURACION_DIA)/Constantes.DURACION_TURNO]
                momento = dia+", "+turno

                println(" ")
                Fichero.escribirLinea(" ")
                println("CAMBIO DE TURNO: "+momento.uppercase())
                Fichero.escribirLinea("CAMBIO DE TURNO: "+momento.uppercase())
            }

            numSalaSeleccionada = -1
            tratadoInterTurno = 0
            tratadoTraumaTurno = 0

            for(med in listaMedicos){
                med.ocupado = false
            }

            //En cada turno distribuyo a los médicos al azar
            listaMedicos.shuffle()

        }

    }while (segundo < Constantes.DURACION_DIA*Constantes.DIAS)

}