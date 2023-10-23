package com.example.shoppinglist.domain.usecase

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.domain.repository.ShopListRepository

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList() : LiveData<List<ShopItem>>{
        return shopListRepository.getShopList()
    }
}