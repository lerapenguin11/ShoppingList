package com.example.shoppinglist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.domain.usecase.DeletePurchaseUseCase
import com.example.shoppinglist.domain.usecase.GetShopListUseCase
import com.example.shoppinglist.domain.usecase.UpdatePurchaseUseCase

class MainViewModel() : ViewModel(){

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deletePurchaseUseCase = DeletePurchaseUseCase(repository)
    private val updatePurchaseUseCase = UpdatePurchaseUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem){
        deletePurchaseUseCase.deletePurchase(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enable = !shopItem.enable)
        updatePurchaseUseCase.updatePurchase(newItem)
    }
}