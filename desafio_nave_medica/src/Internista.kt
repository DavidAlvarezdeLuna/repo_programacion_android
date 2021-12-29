import kotlin.collections.ArrayList

class Internista(nidi:Int? = null, nombre:String? = null, ocupado:Boolean? = null, listaSeguros:ArrayList<String>? = null):Medico(nidi,nombre,ocupado,listaSeguros) {

    class Builder(var nidi:Int? = null, var nombre:String? = null, var ocupado:Boolean? = null, var listaSeguros:ArrayList<String>? = null){

        fun nidi(nidi:Int):Builder{
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
        }
        fun build():Internista{
            return Internista(nidi,nombre,ocupado,listaSeguros)
        }

    }

}