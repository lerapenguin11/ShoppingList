package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.domain.repository.ShopListRepository

class AddPurchaseUseCase(private val shopListRepository: ShopListRepository) {

    fun addPurchase(shopItem: ShopItem){
        shopListRepository.addPurchase(shopItem = shopItem)
    }
}