package com.example.quedadas

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.quedadas.databinding.ActivityMapsCreacionBinding
import com.example.quedadas.databinding.ActivityMiMapsBinding

class MapsCreacion : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener  {

    private val LOCATION_REQUEST_CODE: Int = 0
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMiMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_creacion)

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
        //map.setOnPoiClickListener(this)
        map.setOnMapLongClickListener (this)

        val pos_ini = LatLng(40.4167, -3.7033)
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
            MiMapsActivity.REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    override fun onMapLongClick(p0: LatLng) {
        //map.addMarker(MarkerOptions().position(p0).title("Nuevo marcador"))
        var intentV1 = Intent(this,VentanaEventoNuevo::class.java)
        intentV1.putExtra("lati", p0.longitude.toString())
        intentV1.putExtra("long", p0.latitude.toString())
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