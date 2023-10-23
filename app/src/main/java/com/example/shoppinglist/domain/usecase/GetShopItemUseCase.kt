package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.domain.repository.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItem(shopItem: ShopItem) : ShopItem {
        return shopListRepository.getShopItem(shopItem = shopItem)
    }
}