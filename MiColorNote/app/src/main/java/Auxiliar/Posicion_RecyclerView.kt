package Auxiliar

object Posicion_RecyclerView {

    //Objeto creado para obtener el id de una tarea seleccionada, debido a complicaciones al guardar foto
    var id_recycler:Int = 0

    fun obtenerIdRecycler(id_tocado:Int){
        id_recycler = id_tocado
    }

}