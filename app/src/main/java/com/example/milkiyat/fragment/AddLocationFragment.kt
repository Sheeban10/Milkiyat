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
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.milkiyat.R
import com.example.milkiyat.databinding.FragmentAddLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.Locale

class AddLocationFragment : Fragment() {

    private lateinit var binding: FragmentAddLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationText : AutoCompleteTextView
    private val locationHint = mutableListOf<String>()
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(requireContext(), "YOUR_API_KEY")
        placesClient = Places.createClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddLocationBinding.inflate(layoutInflater)
        val view = binding.root

        locationText = binding.tvLocationText
        val selectedCategory = arguments?.getString("category")
        Log.d("category", "$selectedCategory")


        binding.llLocation.setOnClickListener {
            location()
        }

        val locationHint = arrayListOf<String>("Makhdoom Sahib", "Nowhata", "Khaniyar", "Khayam")

        val arrayAdapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1, locationHint)
        binding.tvLocationText.setAdapter(arrayAdapter)

        binding.btnNext.setOnClickListener {
            if (locationText.text.isNotEmpty()) {
                when (selectedCategory.toString()) {
                    "House" -> addHouseDetails()
                    "Land" -> addLandDetails()
                }
            } else{
                Toast.makeText(requireActivity(), "Please Enter Location to Continue", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun addHouseDetails() {

        val selectedCategory = arguments?.getString("category")
        val loc = binding.locationPage
        loc.visibility = View.GONE

        val houseDetails = AddDetailsHouseFragment()
        val bundle = Bundle()
        bundle.putString("category", selectedCategory)
        bundle.putString("locationData", locationText.text.toString())
        houseDetails.arguments = bundle
        val transaction = parentFragmentManager.beginTransaction()

        transaction
            .replace(R.id.frameLayout ,houseDetails)
            .commit()

        transaction.addToBackStack(null)


    }

    private fun addLandDetails() {

        val category = arguments?.getString("category")
        val loc = binding.locationPage
        loc.visibility = View.GONE

        val landDetails = AddDetailsLandFragment()
        val bundle = Bundle()
        bundle.putString("category", category)
        bundle.putString("locationData", locationText.text.toString())
        landDetails.arguments = bundle
        val transaction = parentFragmentManager.beginTransaction()

        transaction
            .replace(R.id.frameLayout ,landDetails)
            .commit()

        transaction.addToBackStack(null)
    }

    private fun location() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val activityContext = requireActivity() ?: return


        if (ContextCompat.checkSelfPermission(
                activityContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {

                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
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

                        fetchLocationsInCity(city)

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

    private fun fetchLocationsInCity(cityName: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(cityName)
            .build()

        placesClient.findAutocompletePredictions(request).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val predictions = task.result?.autocompletePredictions
                if (predictions != null) {
                    locationHint.addAll(predictions.map { it.getFullText(null).toString() })
                    binding.tvLocationText.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, locationHint))
                }
            } else {
                Log.e("LocationData", "Failed to fetch locations: ${task.exception}")
                Toast.makeText(requireContext(), "Failed to fetch locations", Toast.LENGTH_SHORT).show()
            }
        }
    }


}


