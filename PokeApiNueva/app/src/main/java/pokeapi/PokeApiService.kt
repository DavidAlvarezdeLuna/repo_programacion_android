package pokeapi

import Modelo.PokemonRespuesta
import retrofit2.Call
import retrofit2.http.GET

interface PokeApiService {

    @GET("pokemon")
    fun obtenerListaPokemon(): Call<PokemonRespuesta>
}