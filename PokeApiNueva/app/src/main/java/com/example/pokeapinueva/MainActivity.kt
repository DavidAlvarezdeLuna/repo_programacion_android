package com.example.pokeapinueva

import Modelo.Pokemon
import Modelo.PokemonRespuesta
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pokeapinueva.R
import pokeapi.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val TAG = "POKEDEX"

    private var retrofit: Retrofit? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrofit = Retrofit.Builder()
            .baseUrl("http://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        obtenerDatos()
    }

    private fun obtenerDatos() {

        var service: PokeApiService = retrofit!!.create(PokeApiService::class.java)
        var pokemonRespuestaCall: Call<PokemonRespuesta> = service.obtenerListaPokemon()

        pokemonRespuestaCall.enqueue(object: Callback<PokemonRespuesta>{
            override fun onResponse(call: Call<PokemonRespuesta>, response: Response<PokemonRespuesta>){
                if(response.isSuccessful()){
                    val pokemonRespuesta:PokemonRespuesta = response.body()
                    var listaPokemon:ArrayList<Pokemon> = pokemonRespuesta.getResults()

                    for(i in 0..listaPokemon.size){
                        var p:Pokemon = listaPokemon.get(i)
                        //Log.e("Pokemon: "+p.name)
                    }

                }else{
                    Log.e("mimensaje",response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<PokemonRespuesta>?, t: Throwable?) {
                Log.e("mimensaje","${t!!.message}")
            }
        })

    }
}