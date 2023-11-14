package com.example.milkiyat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.milkiyat.databinding.ActivityHouseCategoriesBinding

class HousesCategoryActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHouseCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHouseCategoriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}