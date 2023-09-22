package com.example.milkiyat


import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
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
    lateinit var locText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        locText = binding.locationText

        val btnLocation = binding.btnLocation


       defaultLocationShow()

        btnLocation.setOnClickListener {
            location()
        }

    }

    private fun defaultLocationShow() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            location()
        } else {
            // Request location permission from the user
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun location() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {

                    val geocoder = Geocoder(this, Locale.getDefault())
                    val address = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )
                    if (address != null && address.isNotEmpty()) {
                        val subCity = address[0].subLocality
                        val city = address[0].locality
                        val country = address[0].countryName

                        // Log the retrieved location data for debugging
                        Log.d("LocationData", "SubCity : $subCity, City: $city, Country: $country")

                        locText.setText("$subCity, $city, $country")
                    } else {
                        Log.e("LocationData", "Geocoder data is not available")
                    }
                } else {
                    Log.e("LocationData", "Location is not available")
                    Toast.makeText(this, "Location is not available", Toast.LENGTH_SHORT).show()

                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
            }
        } else {

        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}