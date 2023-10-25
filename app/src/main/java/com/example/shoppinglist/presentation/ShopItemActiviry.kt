package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppinglist.R
import com.example.shoppinglist.presentation.viewmodel.ShopItemViewModel

class ShopItemActiviry : AppCompatActivity() {
    private lateinit var viewModel : ShopItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_activiry)
    }
}