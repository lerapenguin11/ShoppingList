package com.example.shoppinglist.domain.repository

import com.example.shoppinglist.domain.entity.ShopItem

interface ShopListRepository {

    fun addPurchase(shopItem: ShopItem)

    fun deletePurchase(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int) : ShopItem

    fun getShopList() : List<ShopItem>

    fun updatePurchase(shopItem: ShopItem)
}