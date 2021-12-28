import kotlin.collections.ArrayList

abstract class Medico(nidi:Int, nombre:String, open var ocupado:Boolean, open var listaSeguros:ArrayList<String>):Persona(nidi,nombre) {

    /*open class Builder(var nidi:Int, var nombre:String, var ocupado:Boolean, var listaSeguros:ArrayList<String>){

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
        fun build():Medico{
            return Medico(nidi,nombre,ocupado,listaSeguros)
        }

    }*/

    override fun toString(): String{
        var estado:String = "Disponible"
        if(this.ocupado){
            estado = "Ocupado"
        }
        return this.javaClass.name+" "+this.nidi+", "+this.nombre+", seguros "+this.listaSeguros
    }

}