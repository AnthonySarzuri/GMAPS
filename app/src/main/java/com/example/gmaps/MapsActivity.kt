package com.example.gmaps

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope

import com.example.gmaps.Places.H_Obrero
import com.example.gmaps.Places.Lapaz
import com.example.gmaps.Places.catocilicaPlaza
import com.example.gmaps.Places.univalle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.gmaps.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //zoom para el mapa
        //puedemos delimitar el asercamiento del zoom
        mMap.apply {
            setMinZoomPreference(14f)
            setMaxZoomPreference(18f)
        }

        // Add a marker in Sydney and move the camera


        //agregar un marcador es la tachuela roja
        mMap.addMarker(MarkerOptions().position(univalle).title("Estoy en Univalle ISI"))
        /*mMap.addMarker(MarkerOptions().position(estadium)
            .position(estadium)
            .title("Univalle la mejor")//.snippet("${it.latitude},${it.longitude}")
            .draggable(true)//pra mover el mause

        )*/
        //metodo que valida el mevimeinto o amionacion de la camara virtual del mapa
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(univalle))

        //Todo cofigurar camara personalizada
        /*val cameraUnivalle = CameraPosition.Builder()
            .bearing(0f)//nueva orientacion nuevo norte
            .tilt(0f)//anguklo superior de la camara
            .zoom(16f)
            .target(univalle)
            .build()
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraUnivalle))*/
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Lapaz,12f))
        //Movimiento de la camra usando corrutinas
        lifecycleScope.launch {
            delay(5_000)//tambien asepta este 5_000
            //metodo para animar la transiscion de la camarara

            /*mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(univalle,16f))
            delay(3_000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(megacenter,16f))
            delay(3000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(valleDeLuna,16f))
            delay(3000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(multicine,16f))*/
        }

        //movimiento del mapa

        /* val cameraLpaz = CameraPosition.Builder()
             .bearing(120f)
             .tilt(20f)
             .target(univalle)
             .zoom(15f)
             .build()
         mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraLpaz))
         lifecycleScope.launch {
             delay(5000)
             for(i in 0..50)
             {
                 mMap.animateCamera(CameraUpdateFactory.scrollBy(0f,400f))
                 delay(2000)
             }
         }*/

        //CONFIGURACION DE  BOUNDS EN EL MAPA POSTERIOR SESGO DE MOVILIDAD EN EL MAPA
        //Bouns: con respecto con surOeste y Noreste
        val lapazBounds = LatLngBounds(catocilicaPlaza,H_Obrero)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Lapaz,12f))
        lifecycleScope.launch{
            delay(3_500)
            //mMap.animateCamera(CameraUpdateFactory.newLatLng(univalle, ))
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(lapazBounds,Utils.dp(32)))
            //posicion central
            //lapazBounds.center //es la posicion central de la zona
        }
        //c////////////////
        mMap.setLatLngBoundsForCameraTarget(lapazBounds)
        //como habiltar el punto de ubicaion Azul de la coordenadas en tiempo real en eml Mapa
        mMap.isMyLocationEnabled=false //habilita la luz azul



        //Configuracion de controles en UI y gestures en el mapa
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true //habilitar botones zoom_in zoom_out
            isCompassEnabled=true //habiltar las brujulas
            isRotateGesturesEnabled=false // desabilita la rotacion del mapa
            isMapToolbarEnabled = true //habiliota acceso al mapa de googlke para ver rutas
            isMyLocationButtonEnabled = true // habilita el boton para centrar la posicion
        }

        //trazar el trafico o rutas sercanas a nuestra ubicacion
        mMap.isTrafficEnabled=true//simulamos el trafico de la zon verdes estan libres



        //Ecebto sobre el maoa
        //evento clic sobre el mapa en cvualquier posisicion
        mMap.setOnMapClickListener{
            mMap.addMarker(MarkerOptions().position(it).title("mi nueva posicion").snippet("${it.latitude},${it.longitude}"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
        }

    }
}