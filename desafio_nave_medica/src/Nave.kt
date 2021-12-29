import kotlin.random.Random

object Nave {

    var listaSalas:ArrayList<SalaEspera> = ArrayList()

    override fun toString(): String{
        return "La nave médica Nebulón-B Frigate"
    }

    fun comprobarSalaMasVacia():SalaEspera{
        var llenadaMinima:Int = -1
        var listaSalasMasVacias: ArrayList<SalaEspera> = ArrayList()
        for(sala in listaSalas){
            if(llenadaMinima == -1){
                llenadaMinima = sala.listaPacientes!!.size
                listaSalasMasVacias.add(sala)
            }else{
                if(sala.listaPacientes!!.size < llenadaMinima){
                    listaSalasMasVacias.clear()
                    listaSalasMasVacias.add(sala)
                    llenadaMinima = sala.listaPacientes!!.size
                }else{
                    if(sala.listaPacientes!!.size == llenadaMinima){
                        llenadaMinima = sala.listaPacientes!!.size
                        listaSalasMasVacias.add(sala)
                    }
                }
            }
        }

        lateinit var salaSel:SalaEspera

        if(listaSalasMasVacias.size == 1){
            salaSel = listaSalasMasVacias[0]
        }else{
            if(listaSalasMasVacias.size > 1){
                var azarSala = Random.nextInt(0,listaSalasMasVacias.size)
                salaSel = listaSalasMasVacias[azarSala]
            }
        }

        return salaSel
    }


    fun comprobarSalaMasLlena():SalaEspera{
        var llenadaMaxima:Int = -1
        var listaSalasMasLlenas: ArrayList<SalaEspera> = ArrayList()
        for(sala in listaSalas){

            if(sala.listaPacientes!!.size > llenadaMaxima){
                llenadaMaxima = sala.listaPacientes!!.size
                listaSalasMasLlenas.clear()
                listaSalasMasLlenas.add(sala)

            }else{
                if(sala.listaPacientes!!.size == llenadaMaxima){
                    llenadaMaxima = sala.listaPacientes!!.size
                    listaSalasMasLlenas.add(sala)
                }
            }
        }

        var salaDevuelta:SalaEspera = listaSalas[0]
        if(listaSalasMasLlenas.size == 1){
            salaDevuelta = listaSalasMasLlenas[0]
        }else{
            var azarSala = Random.nextInt(0,listaSalasMasLlenas.size)
            salaDevuelta = listaSalasMasLlenas[azarSala]
        }

        return salaDevuelta
    }

    fun verSalas(){
        for(sala in listaSalas){
            println(sala.toString())
            for(paci in sala.listaPacientes!!){
                println(paci.toString())
            }
        }
    }

}