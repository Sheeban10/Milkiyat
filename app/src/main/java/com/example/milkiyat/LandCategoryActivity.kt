package com.example.milkiyat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.milkiyat.databinding.ActivityLandCategoryBinding

class LandCategoryActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLandCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}