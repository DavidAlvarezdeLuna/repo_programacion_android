import Constantes.Constantes
import Factorias.Factoria
import Auxiliar.Fichero

fun main(){

    //Creo el fichero
    Fichero.iniciarFichero()

    var listaDias:Array<String> = arrayOf("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo")
    var listaTurnos:Array<String> = arrayOf("Primer turno","Segundo turno","Tercer turno")

    //Creo las salas y las añado a la nave
    for(i in 1..Constantes.NUM_SALAS){
         Nave.aniadirSala(i)
    }

    //Creo los medicos
    for(i in 0..Constantes.NUM_MED_ESP){
        Nave.aniadirMedicos()
    }

    //COMIENZA LA SIMULACIÓN
    var segundo:Int = 0
    Nave.obtenerMedicosGuardia()

    var dia:String = listaDias[segundo/Constantes.DURACION_DIA]
    var turno:String = listaTurnos[(segundo%Constantes.DURACION_DIA)/Constantes.DURACION_TURNO]
    var momento:String = dia+", "+turno

    println("SIMULACIÓN DE FUNCIONAMIENTO DE LA NAVE NEBULÓN-B FRIGATE")
    Fichero.escribirLinea("SIMULACIÓN DE FUNCIONAMIENTO DE LA NAVE NEBULÓN-B FRIGATE")
    println(momento.uppercase())
    Fichero.escribirLinea(momento.uppercase())
    println("MÉDICOS DE GUARDIA: ")
    Fichero.escribirLinea("MÉDICOS DE GUARDIA: ")

    for(med in Nave.listaGuardia){
        println(med.toString())
        Fichero.escribirLinea(med.toString())
    }

    do{
        if(segundo%2 == 0){ //cada dos segundos
            //Nave.verSalas()
            //genero un paciente nuevo
            var p:Paciente = Factoria.generarPaciente()

            //Compruebo que sala tiene menos pacientes, e ingreso alli al paciente nuevo
            var salaSel = Nave.comprobarSalaMasVacia()
            salaSel.ingresarPaciente(p)

            println(momento+": "+p.toString() + " ha llegado a la nave médica y ha sido colocado en la sala "+salaSel.numero)
            Fichero.escribirLinea(momento+": "+p.toString() + " ha llegado a la nave médica y ha sido colocado en la sala "+salaSel.numero)
        }

        if(segundo%4 == 0){ //cada cuatro segundos
            //compruebo que sala tiene mas pacientes. Si varias coinciden, saco una al azar
            var salaSeleccionada:SalaEspera = Nave.comprobarSalaMasLlena()
            if(salaSeleccionada.listaPacientes!!.isEmpty()){
                println(momento+": "+"Actualmente no hay pacientes a tratar!")
                Fichero.escribirLinea(momento+": "+"Actualmente no hay pacientes a tratar!")
            }else{
                //saco el paciente y procedo a comprobar si algun médico puede atender al paciente
                var pacienteTratar = salaSeleccionada.obtenerPaciente()

                //saco el tipo de medico que necesita el paciente
                var medicoNecesitado:String = pacienteTratar!!.obtenerTipoMedico()

                if(medicoNecesitado == "Otro"){ //he considerado que la herida "otros" necesita a un tipo de especialista que no hay en la nave en este momento, por lo tanto hay que derivarlo
                    salaSeleccionada.liberarPaciente(pacienteTratar.nidi)
                    println(momento+": "+pacienteTratar.toString()+" ha sido derivado a otra nave hospital para realizar su tratamiento")
                    Fichero.escribirLinea(momento+": "+pacienteTratar.toString()+" ha sido dervado a otra nave hospital para realizar su tratamiento")
                }else{
                    var tratar = false
                    for(med in Nave.listaGuardia){
                        if(!tratar){ //si ya se ha tratado al paciente acaba el for. Si no, compruebo si el siguiente medico puede tratar al paciente
                            tratar=med.puedeTratar(pacienteTratar,medicoNecesitado)

                            if(tratar){ //el medico trata al paciente
                                println(momento+": "+med.toString()+" trata al "+pacienteTratar.toString())
                                Fichero.escribirLinea(momento+": "+med.toString()+" trata al "+pacienteTratar.toString())
                                salaSeleccionada.liberarPaciente(pacienteTratar.nidi)
                            }
                        }
                    }

                    if(!tratar){ //si ningun medico ha podido tratar al paciente, se deriva a otra nave médica
                        salaSeleccionada.liberarPaciente(pacienteTratar.nidi)
                        println(momento+": "+pacienteTratar.toString()+" ha sido derivado a otra nave hospital para realizar su tratamiento")
                        Fichero.escribirLinea(momento+": "+pacienteTratar.toString()+" ha sido dervado a otra nave hospital para realizar su tratamiento")
                    }
                }
            }
        }

        segundo++

        if(segundo%Constantes.DURACION_TURNO == 0){ //cambio de turno y actualizo datos
            if(segundo != Constantes.DURACION_DIA*Constantes.DIAS){

                Thread.sleep(500)

                dia = listaDias[segundo/Constantes.DURACION_DIA]
                turno = listaTurnos[(segundo%Constantes.DURACION_DIA)/Constantes.DURACION_TURNO]
                momento = dia+", "+turno //Indica el dia y turno de la accion registrada
                println(" ")
                Fichero.escribirLinea(" ")
                println("CAMBIO DE TURNO: "+momento.uppercase())
                Fichero.escribirLinea("CAMBIO DE TURNO: "+momento.uppercase())

                //En cada turno se seleccionan los médicos de guardia
                Nave.obtenerMedicosGuardia()
                println("MÉDICOS DE GUARDIA: ")
                Fichero.escribirLinea("MÉDICOS DE GUARDIA: ")

                for(med in Nave.listaGuardia){
                    println(med.toString())
                    Fichero.escribirLinea(med.toString())
                }
            }
        }

    }while (segundo < Constantes.DURACION_DIA*Constantes.DIAS)

    println(" ")
    Fichero.escribirLinea(" ")
    println("FIN DE LA SIMULACIÓN")
    Fichero.escribirLinea("FIN DE LA SIMULACIÓN")

}