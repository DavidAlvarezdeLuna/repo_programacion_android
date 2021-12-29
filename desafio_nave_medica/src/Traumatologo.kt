import kotlin.collections.ArrayList

class Traumatologo(nidi:Int? = null, nombre:String? = null, listaSeguros:ArrayList<String>? = null):Medico(nidi,nombre,listaSeguros) {

    class Builder(var nidi:Int? = null, var nombre:String? = null, var listaSeguros:ArrayList<String>? = null){

        fun nidi(nidi:Int):Builder{
            this.nidi = nidi
            return this
        }
        fun nombre(nombre:String):Builder{
            this.nombre = nombre
            return this
        }
        fun listaSeguros(listaSeguros:ArrayList<String>):Builder{
            this.listaSeguros = listaSeguros
            return this
        }
        fun build():Traumatologo{
            return Traumatologo(nidi,nombre,listaSeguros)
        }

    }

}