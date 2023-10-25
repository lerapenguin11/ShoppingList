package com.example.shoppinglist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.domain.usecase.AddPurchaseUseCase
import com.example.shoppinglist.domain.usecase.GetShopItemUseCase
import com.example.shoppinglist.domain.usecase.UpdatePurchaseUseCase

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addPurchaseUseCase = AddPurchaseUseCase(repository)
    private val updatePurchaseUseCase = UpdatePurchaseUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName : LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount : LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem : LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScree : LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId: Int){
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun addShopItem(inputName : String?, inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            val shopItem = ShopItem(name = name, count = count, enable = true)
            addPurchaseUseCase.addPurchase(shopItem)
            finishWork()
        }
    }

    fun updateShopItem(inputName : String?, inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                updatePurchaseUseCase.updatePurchase(item)
                finishWork()
            }
        }
    }

    private fun parseName(inputName : String?) : String{
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount : String?) : String{
        return try {
            inputCount?.trim() ?: ""
        } catch (e : java.lang.Exception){
            "0"
        }
    }

    private fun validateInput(name : String, count : String) : Boolean{
        var result = true
        if (name.isBlank()){
            _errorInputName.value = true
            result =  false
        }
        if (count.isBlank()){
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName(){
        _errorInputName.value = false
    }

    fun resetErrorInputCount(){
        _errorInputCount.value = false
    }

    private fun finishWork(){
        _shouldCloseScreen.value = Unit
    }
}