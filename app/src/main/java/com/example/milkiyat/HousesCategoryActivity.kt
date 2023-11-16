package com.example.milkiyat

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milkiyat.adapter.ItemCategoriesAdapter
import com.example.milkiyat.databinding.ActivityHouseCategoryBinding
import com.example.milkiyat.model.ItemList
import com.google.firebase.firestore.FirebaseFirestore

class HousesCategoryActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHouseCategoryBinding
    private lateinit var rvHouseCategories: RecyclerView
    private lateinit var itemListAdapter : ItemCategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHouseCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        rvHouseCategories = binding.rvHouseCategories
        rvHouseCategories.layoutManager = GridLayoutManager(this, 2 , LinearLayoutManager.VERTICAL, false)

        getFirebaseitemList("House")


    }

    private fun getFirebaseitemList(category: String) {


        val dbItems = FirebaseFirestore.getInstance()
        val itemRef = dbItems.collection("ItemDetails")

        itemRef.whereEqualTo("category", category)
            .get()
            .addOnSuccessListener {result ->
                val itemList = ArrayList<ItemList>()
                for(document in result){
                    val item = document.toObject(ItemList::class.java)
                    itemList.add(item)
                }
                Log.d(TAG, "Items List Size: ${itemList.size}")

                itemListAdapter = ItemCategoriesAdapter(itemList)
                rvHouseCategories.adapter = itemListAdapter
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error getting items", e)
            }
    }
}