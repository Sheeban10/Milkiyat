package com.example.milkiyat


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.milkiyat.databinding.ActivityMainBinding
import com.example.milkiyat.fragment.HomeFragment
import com.example.milkiyat.fragment.MessagesFragment
import com.example.milkiyat.fragment.NotificationsFragment
import com.example.milkiyat.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav : BottomNavigationView
    lateinit var frameLayout : FrameLayout
    lateinit var btnAdd : FloatingActionButton
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
        bottomNav.background = null /*to disable shadow of background in bottom nav*/
        bottomNav.menu.getItem(2).isEnabled = false /*to make menu item unclickable*/
        frameLayout = binding.frameMain


       bottomNavOnItemSelect()

        btnAdd = binding.fabAdd

        btnAdd.setOnClickListener {
            fabAdd()
        }


    }

    private fun fabAdd() {

        val addChoiceView = LayoutInflater.from(this).inflate(R.layout.add_item_dialog, null)
        val addChoice = AlertDialog.Builder(this)
                        .setView(addChoiceView)
                        .setTitle("Choose Category")
                        .show()

        val houseAdd = addChoiceView.findViewById<CardView>(R.id.imgHouseDialog)
        val landAdd = addChoiceView.findViewById<CardView>(R.id.imgLandDialog)

        houseAdd.setOnClickListener {
            houseAdd()
            addChoice.hide()
        }

        landAdd.setOnClickListener {
            landAdd()
            addChoice.hide()
        }

    }

     fun houseAdd(){

        val intent = Intent(this, AddLocation::class.java)
         intent.putExtra("category", "House")
        startActivity(intent)

    }

     fun landAdd(){
        val intent = Intent(this , AddLocation::class.java)
         intent.putExtra("category", "Land")
        startActivity(intent)
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

        val homeFragment = HomeFragment()
        Log.d("MainActivity", "Home item selected")
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameMain, homeFragment)
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