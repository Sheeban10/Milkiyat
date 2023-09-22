package com.example.milkiyat

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.milkiyat.databinding.ActivitySplashBinding

class SplashActivity :AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val SPLASH_TIME_OUT: Long = 2700
    private val permissionRequestCode = 123

    private val permissions = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.POST_NOTIFICATIONS

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        checkAndRequestPermission()
    }

    private fun checkAndRequestPermission(){
        val permissionsToRequest = mutableListOf<String>()

        for (permission in permissions){
            if (ContextCompat.checkSelfPermission(this, permission) !=PackageManager.PERMISSION_GRANTED){
                permissionsToRequest.add(permission)
            }
        }
        if (permissionsToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permissionsToRequest.toTypedArray(),permissionRequestCode)
        }
        else {
            startMainActivity()
        }
    }

    private fun startMainActivity() {

        Handler().postDelayed(
            {
                val locationData = "Area, City, Country"

                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("locationData", locationData)
                startActivity(intent)
                finish()
            },SPLASH_TIME_OUT
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == permissionRequestCode){
            var allPermissionsGranted = true

            for(i in grantResults.indices){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    allPermissionsGranted = false
                    break
                }
            }

            if (allPermissionsGranted){
                startMainActivity()
            }
            else {
                if (permissions.any{ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED}) {
                }
                startMainActivity()
            }
        }
    }
}

