package com.example.milkiyat.model

import android.net.Uri


data class ItemDetails(
    val images : ArrayList<Uri>,
    val category : String = "",
    val location : String = "",
    val title : String = "",
    val description : String = "",
    val price : String = ""
)
