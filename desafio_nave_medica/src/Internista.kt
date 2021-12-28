import kotlin.collections.ArrayList

class Internista(nidi:Int, nombre:String, ocupado:Boolean, listaSeguros:ArrayList<String>):Medico(nidi,nombre,ocupado,listaSeguros) {

    class Builder(var nidi:Int, var nombre:String, var ocupado:Boolean, var listaSeguros:ArrayList<String>){

        /*fun nidi(nidi:Int):Builder{
            this.nidi = nidi
            return this
        }
        fun nombre(nombre:String):Builder{
            this.nombre = nombre
            return this
        }
        fun ocupado(ocupado:Boolean):Builder{
            this.ocupado = ocupado
            return this
        }
        fun listaSeguros(listaSeguros:ArrayList<String>):Builder{
            this.listaSeguros = listaSeguros
            return this
        }*/
        fun build():Internista{
            return Internista(nidi,nombre,ocupado,listaSeguros)
        }

    }

}