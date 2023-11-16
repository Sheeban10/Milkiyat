package com.example.milkiyat

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milkiyat.adapter.ItemCategoriesAdapter
import com.example.milkiyat.databinding.ActivityLandCategoryBinding
import com.example.milkiyat.model.ItemList
import com.google.firebase.firestore.FirebaseFirestore

class LandCategoryActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLandCategoryBinding
    private lateinit var rvLandCategories: RecyclerView
    private lateinit var itemListAdapter : ItemCategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        rvLandCategories = binding.rvHouseCategories
        rvLandCategories.layoutManager = GridLayoutManager(this, 2 , LinearLayoutManager.VERTICAL, false)

        getFirebaseitemList("Land")

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
                Log.d(ContentValues.TAG, "Items List Size: ${itemList.size}")

                itemListAdapter = ItemCategoriesAdapter(itemList)
                rvLandCategories.adapter = itemListAdapter
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error getting items", e)
            }
    }
}