import kotlin.random.Random

object Nave {

    var listaSalas:ArrayList<SalaEspera> = ArrayList()

    override fun toString(): String{
        return "La nave médica Nebulón-B Frigate"
    }

    fun ingresarPaciente(p:Paciente):Int{
        var llenadaMinima:Int = -1
        var listaSalasMasVacias: ArrayList<SalaEspera> = ArrayList()
        for(sala in listaSalas){
            if(llenadaMinima == -1){
                llenadaMinima = sala.listaPacientes.size
                listaSalasMasVacias.add(sala)
            }else{
                if(sala.listaPacientes.size < llenadaMinima){
                    listaSalasMasVacias.clear()
                    listaSalasMasVacias.add(sala)
                    llenadaMinima = sala.listaPacientes.size
                }else{
                    if(sala.listaPacientes.size == llenadaMinima){
                        llenadaMinima = sala.listaPacientes.size
                        listaSalasMasVacias.add(sala)
                    }
                }
            }
        }

        var numSalaSeleccionada = -1

        if(listaSalasMasVacias.size == 1){
            listaSalasMasVacias[0].listaPacientes.put(p.nidi,p)
            numSalaSeleccionada = listaSalasMasVacias[0].numero
        }else{
            if(listaSalasMasVacias.size > 1){
                var azarSala = Random.nextInt(0,listaSalasMasVacias.size)
                listaSalasMasVacias[azarSala].listaPacientes.put(p.nidi,p)
                numSalaSeleccionada = listaSalasMasVacias[azarSala].numero
            }
        }

        for(sala in listaSalas){
            println("SALA "+sala.numero)
            for(paci in sala.listaPacientes){
                println(paci.toString())
            }
        }

        return numSalaSeleccionada
    }

    fun comprobarSalaMasLlena():SalaEspera{
        var llenadaMaxima:Int = -1
        var listaSalasMasLlenas: ArrayList<SalaEspera> = ArrayList()
        for(sala in listaSalas){

            println("SALA NUMERO "+sala.numero+" SIZE "+sala.listaPacientes.size)

            if(sala.listaPacientes.size > llenadaMaxima){
                llenadaMaxima = sala.listaPacientes.size
                listaSalasMasLlenas.clear()
                listaSalasMasLlenas.add(sala)

            }else{
                if(sala.listaPacientes.size == llenadaMaxima){
                    llenadaMaxima = sala.listaPacientes.size
                    listaSalasMasLlenas.add(sala)
                }
            }
        }

        var salaDevuelta:SalaEspera = listaSalas[0]
        if(listaSalasMasLlenas.size == 1){
            println("SALA MAS LLENA: "+listaSalasMasLlenas)
            salaDevuelta = listaSalasMasLlenas[0]
        }else{
            println("SALAS MAS LLENAS: "+listaSalasMasLlenas)
            var azarSala = Random.nextInt(0,listaSalasMasLlenas.size)
            salaDevuelta = listaSalasMasLlenas[azarSala]
        }

        return salaDevuelta
    }

}