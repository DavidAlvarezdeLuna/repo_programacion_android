
class Paciente (override var nidi:Int, override var nombre:String, var seguro:String, var herida:String, var prioridad:Int):Persona(nidi,nombre){

    open class Builder(var nidi:Int, var nombre:String, var seguro:String, var herida:String, var prioridad:Int){

        /*fun nidi(nidi:Int):Builder{
            this.nidi = nidi
            return this
        }
        fun nombre(nombre:String):Builder{
            this.nombre = nombre
            return this
        }*/
        fun seguro(seguro:String):Builder{
            this.seguro = seguro
            return this
        }
        fun herida(herida:String):Builder{
            this.herida = herida
            return this
        }
        fun prioridad(prioridad:Int):Builder{
            this.prioridad = prioridad
            return this
        }
        fun build():Paciente{
            return Paciente(nidi,nombre,seguro,herida,prioridad)
        }

    }

    override fun toString(): String{
        return this.javaClass.name+" "+this.nidi+", "+this.nombre+" (seguro "+this.seguro+", prioridad "+this.prioridad+")"
    }

}