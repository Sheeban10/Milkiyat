package com.example.milkiyat

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.milkiyat.databinding.ActivityAddLocationBinding
import com.example.milkiyat.fragment.AddLocationFragment
import com.google.android.gms.location.FusedLocationProviderClient


class AddItem: AppCompatActivity() {

    private lateinit var binding: ActivityAddLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLocationBinding.inflate(layoutInflater)
        val view = binding.root


        val selectedCategory = intent.getStringExtra("category")
        Log.d("category", "$selectedCategory")

        val addLocationFragment = AddLocationFragment()
        val bundle = Bundle()
        bundle.putString("category", selectedCategory)

        addLocationFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, addLocationFragment)
            .commit()



        setContentView(view)
    }


}