package com.example.shoppinglist.data

import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.domain.repository.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository{

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun addPurchase(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deletePurchase(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun updatePurchase(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        updatePurchase(shopItem)
    }
}