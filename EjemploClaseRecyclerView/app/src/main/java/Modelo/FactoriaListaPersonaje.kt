package Modelo

object FactoriaListaPersonaje {
    fun generaLista(cant:Int):ArrayList<Personaje> {
        var lista = ArrayList<Personaje>(1)
        for(i in 1..cant){
            lista.add(FactoriaPersonaje.generaPersonaje())
        }
        return lista
    }
}