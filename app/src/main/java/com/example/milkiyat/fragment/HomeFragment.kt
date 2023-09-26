package com.example.milkiyat.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.milkiyat.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class HomeFragment : Fragment() {

    lateinit var locText : TextView
    lateinit var btnLocation : Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        locText = view.findViewById(R.id.locationText)

        btnLocation = view.findViewById(R.id.btnLocation)

        defaultLocationShow()

        btnLocation.setOnClickListener {
            location()
        }

        return view
    }

    private fun defaultLocationShow() {
        val activityContext = activity ?: return
        if (ContextCompat.checkSelfPermission(
                activityContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            location()
        } else {
            // Request location permission from the user
            ActivityCompat.requestPermissions(
                activityContext,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun location() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val activityContext = activity ?: return

        if (ContextCompat.checkSelfPermission(
                activityContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {

                    val geocoder = Geocoder(activityContext, Locale.getDefault())
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
                    Toast.makeText(Activity(), "Location is not available", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}