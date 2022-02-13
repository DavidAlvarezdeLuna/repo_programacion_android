package com.example.ejquedadas

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ejquedadas.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.ejquedadas.databinding.ActivityMapsCrearEventoBinding
import com.google.android.gms.maps.model.Marker

class MapsCrearEvento : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private val LOCATION_REQUEST_CODE: Int = 0
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_crear_evento)

        /*binding = ActivityMapsCrearEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)*/

        createMapFragment()

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        //Se pueden seleccionar varios tiops de mapas:
        //  None --> no muestra nada, solo los marcadores. (MAP_TYPE_NONE)
        //  Normal --> El mapa por defecto. (MAP_TYPE_NORMAL)
        //  Satélite --> Mapa por satélite.  (MAP_TYPE_SATELLITE)
        //  Híbrido --> Mapa híbrido entre Normal y Satélite. (MAP_TYPE_HYBRID) Muestra satélite y mapas de carretera, ríos, pueblos, etc... asociados.
        //  Terreno --> Mapa de terrenos con datos topográficos. (MAP_TYPE_TERRAIN)
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        //map.setOnMyLocationButtonClickListener(this)
        //map.setOnMyLocationClickListener(this)
        //map.setOnPoiClickListener(this)
        map.setOnMapLongClickListener (this)
        //map.setOnMarkerClickListener(this)

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        val madrid = LatLng(40.4167,-3.70325)

        //map.addMarker(MarkerOptions().position(posEvento).title("Marcador evento"))
        map.moveCamera(CameraUpdateFactory.newLatLng(madrid))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(madrid,15f))

    }

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    @SuppressLint("MissingSuperCall", "MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    /**
     * Con el parámetro crearemos un marcador nuevo. Este evento se lanzará al hacer un long click en alguna parte del mapa.
     */
    override fun onMapLongClick(p0: LatLng) {
        //map.addMarker(MarkerOptions().position(p0).title("Nuevo marcador"))
        val lat = p0.latitude
        val long = p0.longitude
        Toast.makeText(this, lat.toString() + " " + long.toString(), Toast.LENGTH_LONG).show()


        var intentV1 = Intent(this,CrearEvento::class.java)
        intentV1.putExtra("lat",lat.toString())
        intentV1.putExtra("long",long.toString())
        startActivity(intentV1)
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