package com.example.shoppinglist.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.presentation.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : ShopAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this){ shopList ->
            adapter.submitList(shopList.sortedBy { it.id })
        }
        val btAddItemShop = findViewById<FloatingActionButton>(R.id.buttom_add_shop_item)
        btAddItemShop.setOnClickListener {
            val intent = ShopItemActiviry.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setRecyclerView(){
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopAdapter()
        rvShopList.adapter = adapter
        with(rvShopList){
            recycledViewPool.setMaxRecycledViews(ShopAdapter.VIEW_TYPE_ENABLE, ShopAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(ShopAdapter.VIEW_TYPE_DISABLE, ShopAdapter.MAX_POOL_SIZE)

        }
        setupLongClickLitener()
        setupClickListener()
        setupSwipeListener(rvShopList)
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {
        adapter.onShopItemClick = {
            val intent = ShopItemActiviry.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickLitener() {
        adapter.onShopItemLongListener = {
            viewModel.changeEnableState(it)
        }
    }
}