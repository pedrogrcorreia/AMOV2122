package pt.isec.a21280305.locationexample

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
//import android.location.LocationListener
//import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import pt.isec.a21280305.locationexample.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    val ISEC = LatLng(40.1925, -8.4115)
    val DEIS = LatLng(40.1925, -8.4128)

    private var fineLocationPermission = false
    private var coarseLocationPermission = false

    private lateinit var binding : ActivityMainBinding
    //lateinit var lm : LocationManager

    private lateinit var fLoc : FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var mMapFragment = SupportMapFragment.newInstance()
        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.map, mMapFragment)
        fragmentTransaction.commit()

        (supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)?.getMapAsync(this)
        
        //lm = getSystemService(LOCATION_SERVICE) as LocationManager

        fLoc = FusedLocationProviderClient(this)

        fineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        coarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if(!fineLocationPermission && !coarseLocationPermission)
            requestLocationPermission.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
    }

    override fun onResume(){
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause(){
        super.onPause()
        stopLocationUpdates()
    }

    private val requestLocationPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        fineLocationPermission = permissions.get(Manifest.permission.ACCESS_FINE_LOCATION) ?: false
        coarseLocationPermission = permissions.get(Manifest.permission.ACCESS_COARSE_LOCATION) ?: false
        startLocationUpdates()
    }

    private var locationEnabled = false

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        if (locationEnabled || (!fineLocationPermission && !coarseLocationPermission))
            return

//        currentLocation = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
//            ?: Location("AMOV").apply {
//                latitude = -12.34  // valores para teste na aula
//                longitude = 43.21  //   colocar em vez de PASSIVE, por exemplo, "AMOV"
//            }
//
//        if (fineLocationPermission)
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10f,locationCallback)
//        else // coarseLocationPermission é true
//            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,10f,locationCallback)
        fLoc.lastLocation.addOnSuccessListener { location ->
            currentLocation = location
        }
        fLoc.requestLocationUpdates(locReq, locationCallback, null)

        locationEnabled = true
    }

    val locReq = LocationRequest.create().apply {
        interval = 4000
        fastestInterval = 2000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime = 10000
    }
    var locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            Log.i(TAG, "onLocationResult: ")
            p0?.locations?.forEach {
                Log.i(TAG, "locationCallback: ${it.latitude} ${it.longitude}")
            }
        }
    }

    fun stopLocationUpdates(){
        if(!locationEnabled)
            return
        //lm.removeUpdates(locationCallback)
        fLoc.removeLocationUpdates(locationCallback)
        locationEnabled = false
    }

//    val locationCallback = LocationListener {
//        location -> currentLocation = location
//    }

    private var currentLocation = Location("")
        get() = field
        set(value){
            field = value
            binding.tvLat.text = String.format("%10.5f", value.latitude)
            binding.tvLon.text = String.format("%10.5f", value.longitude)
        }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isZoomGesturesEnabled = true
        val cp = CameraPosition.Builder().target(ISEC).zoom(17f)
            .bearing(0f).tilt(0f).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cp))
        map.addCircle(CircleOptions().center(ISEC).radius(150.0)
            .fillColor(Color.argb(128, 128, 128, 128))
            .strokeColor(Color.rgb(128, 0, 0)).strokeWidth(4f))
        val mo = MarkerOptions().position(ISEC).title("ISEC-IPC")
            .snippet("Instituto Superior de Engenharia de Coimbra")
        val isec = map.addMarker(mo)
        isec?.showInfoWindow()
        map.addMarker(MarkerOptions().position(DEIS).title("DEIS-ISEC"))
    }
}

