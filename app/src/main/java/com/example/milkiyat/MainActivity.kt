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
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainerView
import com.example.milkiyat.databinding.ActivityMainBinding
import com.example.milkiyat.fragment.HomeFragment
import com.example.milkiyat.fragment.MessagesFragment
import com.example.milkiyat.fragment.NorificationsFragment
import com.example.milkiyat.fragment.ProfileFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav : BottomNavigationView
    lateinit var frameLayout : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        homeFragment()


        bottomNav = binding.btmAppbar
        frameLayout = binding.frameMain

        bottomNav.setOnItemReselectedListener {

            when(it.itemId){

                R.id.home -> {
                    homeFragment()
                }

                R.id.messages -> {supportFragmentManager.beginTransaction()
                    .replace(R.id.frameMain, MessagesFragment())
                    .commit()}

                R.id.notifications -> {supportFragmentManager.beginTransaction()
                    .replace(R.id.frameMain, NorificationsFragment())
                    .commit()}

                R.id.profile -> {supportFragmentManager.beginTransaction()
                    .replace(R.id.frameMain, ProfileFragment())
                    .commit()}
            }
        }


    }

    private fun homeFragment() {
        Log.d("MainActivity", "Home item selected")
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameMain, HomeFragment())
            .commit()
    }

}