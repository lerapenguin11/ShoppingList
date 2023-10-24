package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.entity.ShopItem

class ShopAdapter : ListAdapter<ShopItem, ShopViewHolder>(ShopItemDiffCallback()){

    var onShopItemLongListener : ((ShopItem) -> Unit)? = null
    var onShopItemClick : ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {

        val layout = when(viewType){
            VIEW_TYPE_DISABLE -> R.layout.item_shop_disable
            VIEW_TYPE_ENABLE -> R.layout.item_shop_enable
            else -> throw java.lang.RuntimeException("Unknow view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shop = getItem(position)
        holder.name.text = shop.name
        holder.count.text = shop.count
        holder.itemView.setOnLongClickListener {
            onShopItemLongListener?.invoke(shop)
            true
        }

        holder.itemView.setOnClickListener{
            onShopItemClick?.invoke(shop)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if(item.enable){
            VIEW_TYPE_ENABLE
        } else{
            VIEW_TYPE_DISABLE
        }
    }

    companion object{
        const val VIEW_TYPE_ENABLE = 100
        const val VIEW_TYPE_DISABLE = 101
        const val MAX_POOL_SIZE = 15
    }
}