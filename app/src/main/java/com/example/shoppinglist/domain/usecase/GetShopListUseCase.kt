package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.domain.repository.ShopListRepository

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList() : List<ShopItem>{
        return shopListRepository.getShopList()
    }
}