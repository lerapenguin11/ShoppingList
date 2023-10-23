package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.domain.repository.ShopListRepository

class DeletePurchaseUseCase(private val shopListRepository: ShopListRepository) {

    fun deletePurchase(shopItem: ShopItem){
        shopListRepository.deletePurchase(shopItem = shopItem)
    }
}