package Factorias

import kotlin.collections.ArrayList
import Constantes.Constantes
import Internista
import kotlin.random.Random
import Paciente
import SalaEspera
import Traumatologo

object Factoria {

    var numeroSala:Int = 0

    var nidiPersona:Int = 0
    //Creo una lista de 20 nombres para que haya variabilidad suficiente en los nombres de los pacientes y los médicos
    var listaNombres:Array<String> = arrayOf("Pepe","Pepa","Juan","Juana","Miguel","Laura","Estrella","Jesus","Lola","Pedro","Ana","Marta","Eugenio","Veronica","Bruno","Alfonso","Sara","Paula","Ernesto","Wenceslao")
    var listaSeguros:Array<String> = arrayOf("Sanitroopers","Vaderslas","Yodacare")
    var listaHeridas:Array<String> = arrayOf("quemadura","impacto")

    fun generarPaciente():Paciente{
        var azarNombre:String = listaNombres[Random.nextInt(0, listaNombres.size)]
        var azarSeguro:String = listaSeguros[Random.nextInt(0, listaSeguros.size)]
        var azarHerida:String = listaHeridas[Random.nextInt(0, listaHeridas.size)]

        var p:Paciente = Paciente.Builder(nidiPersona,azarNombre,azarSeguro,azarHerida,Random.nextInt(0,3)).build()
        nidiPersona++
        return p
    }

    fun generarTraumatologo():Traumatologo{
        var azarNombre:String = listaNombres[Random.nextInt(0, listaNombres.size)]
        //Cada médico pertenece a dos seguros. Saco uno al azar de la lista de seguros, y me aseguro de que el siguiente no sea el mismo
        var azar1:Int = Random.nextInt(0, listaSeguros.size)
        var azar2:Int = azar1

        do{
            azar2 = Random.nextInt(0, listaSeguros.size)
        }while (azar1 == azar2)

        //Añado los dos seguros a una lista propia de cada medico
        var segurosMedico:ArrayList<String> = ArrayList<String>()
        segurosMedico.add(listaSeguros[azar1])
        segurosMedico.add(listaSeguros[azar2])

        var t:Traumatologo = Traumatologo.Builder(nidiPersona,azarNombre,false,segurosMedico).build()
        nidiPersona++
        return t
    }

    fun generarInternista():Internista{
        var azarNombre:String = listaNombres[Random.nextInt(0, listaNombres.size)]
        //Cada médico pertenece a dos seguros. Saco uno al azar de la lista de seguros, y me aseguro de que el siguiente no sea el mismo
        var azar1:Int = Random.nextInt(0, listaSeguros.size)
        var azar2:Int = azar1

        do{
            azar2 = Random.nextInt(0, listaSeguros.size)
        }while (azar1 == azar2)

        //Añado los dos seguros a una lista propia de cada medico
        var segurosMedico:ArrayList<String> = ArrayList<String>()
        segurosMedico.add(listaSeguros[azar1])
        segurosMedico.add(listaSeguros[azar2])

        var i:Internista = Internista.Builder(nidiPersona,azarNombre,false,segurosMedico).build()
        nidiPersona++
        return i
    }

    fun generarSala(numero:Int):SalaEspera{

        var listaPacientesSala:HashMap<Int,Paciente> = HashMap()
        var s:SalaEspera = SalaEspera.Builder(numero,listaPacientesSala).build()
        return s
    }

}