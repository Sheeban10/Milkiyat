package com.example.milkiyat

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.milkiyat.databinding.ActivityAddLocationBinding
import com.example.milkiyat.fragment.AddDetailsHouseFragment
import com.example.milkiyat.fragment.AddDetailsLandFragment
import com.example.milkiyat.fragment.HomeFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale


class AddLocation: AppCompatActivity() {

    private lateinit var binding: ActivityAddLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLocationBinding.inflate(layoutInflater)
        val view = binding.root


        locationText = binding.locationText


        binding.llLocation.setOnClickListener {
            location()
        }

        binding.btnNext.setOnClickListener {
            val selectedCategory = intent.getStringExtra("category")

            if (locationText.text.isNotEmpty()) {
                when (selectedCategory) {
                    "House" -> addHouseDetails()
                    "Land" -> addLandDetails()
                }
            } else{
                Toast.makeText(this, "Please Enter Location to Continue", Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(view)
    }


    private fun addHouseDetails() {

        val loc = binding.locationPage
        loc.visibility = View.GONE

        val houseDetails = AddDetailsHouseFragment()
        val bundle = Bundle()
        bundle.putString("locationData", locationText.toString())
        houseDetails.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout ,houseDetails)
            .commit()

    }

    private fun addLandDetails() {

        val loc = binding.locationPage
        loc.visibility = View.GONE

        val landDetails = AddDetailsLandFragment()
        val bundle = Bundle()
        bundle.putString("locationData", locationText.toString())
        landDetails.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout ,landDetails)
            .commit()

    }

    private fun location() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val activityContext = this ?: return


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
                        val pincode = address[0].postalCode
                        val country = address[0].countryName

                        // Log the retrieved location data for debugging
                        Log.d("LocationData", "SubCity : $subCity, City: $city, Country: $country")

                        val locDetails = "$subCity, $city, $pincode"
                        locationText.setText(locDetails)

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
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}