package Modelo

class PokemonRespuesta {
    private var results: ArrayList<Pokemon> = ArrayList()

    fun getResults():ArrayList<Pokemon>{
        return results
    }

    fun setResults(results:ArrayList<Pokemon>){
        this.results = results
    }

}