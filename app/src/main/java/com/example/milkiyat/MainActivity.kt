package com.example.milkiyat


import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.milkiyat.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.location.setOnClickListener {
            getLocationAndDisplay(binding)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        } else {
            getLocationAndDisplay(binding)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val binding = ActivityMainBinding.inflate(layoutInflater)
            getLocationAndDisplay(binding)
        }
    }

    private fun getLocationAndDisplay(binding: ActivityMainBinding) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses: List<Address> =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>

                    if (addresses.isNotEmpty()) {
                        val address = addresses[0]
                        val addressText = "${address.getAddressLine(0)}"
                        binding.locationtext.text = "$addressText"

                        binding.locationtext.isSelected = true
                    } else {
                        binding.locationtext.text = "Location not available"
                    }
                } else {
                    binding.locationtext.text = "Location not available"
                }
            }.addOnFailureListener { exception ->
                binding.locationtext.text = "Location retrieval failed: ${exception.message}"
            }
        }
    }
}


//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//
//        binding.location.setOnClickListener{
//
//            checkLocationPermission()
//        }
//
//    }
//
//
//    private fun checkLocationPermission(){
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED){
//
//            checkGPS()
//        }
//        else{
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)
//        }
//    }
//
//    private fun checkGPS(){
//
//        locationRequest = com.google.android.gms.location.LocationRequest.create()
//        locationRequest.priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
//        locationRequest.interval = 5000
//        locationRequest.fastestInterval = 2000
//
//
//        val builder = LocationSettingsRequest.Builder()
//            .addLocationRequest(locationRequest)
//
//        builder.setAlwaysShow(true)
//
//        val result = LocationServices.getSettingsClient(
//            this.applicationContext
//        ).checkLocationSettings(builder.build())
//
//        result.addOnCompleteListener { task->
//
//            try{
//                val responce = task.getResult(
//                    ApiException::class.java
//                )
//
//                getuserLocation()
//
//            }catch(e : ApiException){
//
//                e.printStackTrace()
//
//                when(e.statusCode){
//                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
//                        val resolveApiException = e as ResolvableApiException
//                        resolveApiException.startResolutionForResult(this, 200)
//
//                    }catch (sendIntentException : IntentSender.SendIntentException) {
//                    }
//                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
//
//                }
//            }
//        }
//    }
//
//    private fun getuserLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
//
//            val location = task.getResult()
//
//            if (location != null){
//                 try {
//
//                     val geocoder = Geocoder(this, Locale.getDefault())
//
//                     val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//
//                     val address_line = address?.get(0)?.getAddressLine(0)
//                     binding.locationtext.setText(address_line)
//
//                     val address_location = address?.get(0)?.getAddressLine(0)
//
//                     openLocation(address_location.toString())
//
//                 }catch (e : IOException){
//
//                 }
//            }
//
//        }
//    }
//
//    private fun openLocation(location: String) {
//
//        binding.locationtext.setOnClickListener {
//            if (!binding.locationtext.text.isEmpty())
//
//            val uri = Uri.parse("geo:0, 0?q=$location")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            intent.setPackage("com.google.android.apps.maps")
//            startActivity(intent)
//        }
//    }
//}