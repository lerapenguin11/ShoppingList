package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.domain.repository.ShopListRepository

class UpdatePurchaseUseCase(private val shopListRepository: ShopListRepository) {

    fun updatePurchase(shopItem: ShopItem){
        shopListRepository.updatePurchase(shopItem = shopItem)
    }
}