
open class Persona(open var nidi:Int, open var nombre:String) {

    class Builder(var nidi:Int, var nombre:String){
        fun nidi(nidi:Int):Builder{
            this.nidi = nidi
            return this
        }
        fun nombre(nombre:String):Builder{
            this.nombre = nombre
            return this
        }
        fun build():Persona{
            return Persona(nidi,nombre)
        }
    }

}