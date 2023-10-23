package com.example.shoppinglist.domain.entity

data class ShopItem(
    val name : String,
    val count : String,
    val enable : Boolean,
    var id : Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = -1
    }
}
