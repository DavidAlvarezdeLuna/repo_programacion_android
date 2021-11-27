package Modelo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListadoPokemon(@SerializedName("results") var listPokemon: ArrayList<Pokemon> = ArrayList()) {

}