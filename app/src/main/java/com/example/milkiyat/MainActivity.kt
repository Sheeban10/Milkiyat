package com.example.milkiyat


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.FrameLayout
import com.example.milkiyat.databinding.ActivityMainBinding
import com.example.milkiyat.fragment.HomeFragment
import com.example.milkiyat.fragment.MessagesFragment
import com.example.milkiyat.fragment.NotificationsFragment
import com.example.milkiyat.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav : BottomNavigationView
    lateinit var frameLayout : FrameLayout
    var previousMenuItem : MenuItem? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState != null) {
            val selectedFragment = savedInstanceState.getInt("selectedFragment")
        } else {
            homeFragment()
        }


        bottomNav = binding.btmAppbar
        frameLayout = binding.frameMain


       bottomNavOnItemSelect()


    }

    private fun bottomNavOnItemSelect(){

        val name = intent.getStringExtra("name")
        val photo = intent.getStringExtra("photo")
        Log.d("photo", "photo received $name")
        Log.d("photo", "photo received $photo")

        bottomNav.setOnItemSelectedListener {
            when(it.itemId){

                R.id.home ->{
                    homeFragment()
                }

                R.id.messages -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameMain, MessagesFragment())
                        .commit()

                }

                R.id.notifications -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameMain, NotificationsFragment())
                        .commit()
                }

                R.id.profile -> {
                    val fragment = ProfileFragment()
                    val bundle = Bundle()
                    bundle.putString("Name", name)
                    bundle.putString("Photo", photo)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameMain, fragment)
                        .commit()
                }
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun homeFragment() {
        Log.d("MainActivity", "Home item selected")
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameMain, HomeFragment())
            .commit()
    }

    override fun onBackPressed() {
        val frag =supportFragmentManager.findFragmentById(R.id.frameMain)

        when(frag){
            !is HomeFragment ->homeFragment()


            else -> super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save the current fragment's tag or ID
        outState.putInt("selectedFragment", bottomNav.selectedItemId)
        super.onSaveInstanceState(outState)
    }

}