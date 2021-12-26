import Constantes.Constantes
import Factorias.Factoria

fun main(){

    //Creo los medicos
    for(i in 0..Constantes.NUM_MED_ESP){
        Factoria.generarTraumatologo()
        Factoria.generarInternista()
    }

    //Creo las salas
    for(i in 0..Constantes.NUM_SALAS){
        Factoria.generarSala(i)
    }


    //COMIENZA LA SIMULACIÓN
    var segundo:Int = 0

    do{

        if(segundo%2 == 0){ //cada dos segundos
            //genero un paciente nuevo
            var p:Paciente = Factoria.generarPaciente()
            //ALERTA: ENCAPSULAR BIEN
            //No deberia poder hacer p.herida
        }

        if(segundo%4 == 0){ //cada cuatro segundos

        }

        segundo++

    }while (segundo < 210)

}