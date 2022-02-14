package com.example.desafio_viaje

import Modelo.Evento
import Modelo.Ubicacion
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.desafio_viaje.databinding.ActivityMapsCreacionBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.desafio_viaje.databinding.ActivityMapsUbicacionBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MapsUbicacion : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private val LOCATION_REQUEST_CODE: Int = 0
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsCreacionBinding

    private val db = Firebase.firestore
    private val TAG = "David"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_ubicacion)

        createMapFragment()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //Se pueden seleccionar varios tiops de mapas:
        //  None --> no muestra nada, solo los marcadores. (MAP_TYPE_NONE)
        //  Normal --> El mapa por defecto. (MAP_TYPE_NORMAL)
        //  Satélite --> Mapa por satélite.  (MAP_TYPE_SATELLITE)
        //  Híbrido --> Mapa híbrido entre Normal y Satélite. (MAP_TYPE_HYBRID) Muestra satélite y mapas de carretera, ríos, pueblos, etc... asociados.
        //  Terreno --> Mapa de terrenos con datos topográficos. (MAP_TYPE_TERRAIN)
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        //map.setOnPoiClickListener(this)
        map.setOnMapLongClickListener (this)

        var eve: Evento = intent.getSerializableExtra("even") as Evento

        val pos_ini = LatLng(eve.latitud, eve.longitud)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos_ini, 14f))
    }

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }

    @SuppressLint("MissingSuperCall", "MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            MapsCreacion.REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    override fun onMapLongClick(p0: LatLng) {

        var titulo: String = intent.getStringExtra("titulo") as String
        var eve: Evento = intent.getSerializableExtra("even") as Evento

        var ubi:Ubicacion = Ubicacion(titulo,p0.latitude,p0.longitude)

        eve.lista_ubicaciones.add(ubi)

        db.collection("eventos").document(eve.codigo).update("ubicaciones", eve.lista_ubicaciones)

        var intentV1 = Intent(this, VentanaInfoEvento::class.java)
        intentV1.putExtra("even",eve)
        this.startActivity(intentV1)

        finish()
    }

    fun createMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        /*
        getMapAsync(this) necesita que nuestra activity implemente la función onMapReady() y para ello tenemos que añadir la interfaz
        OnMapReadyCallback.
         */
    }
}