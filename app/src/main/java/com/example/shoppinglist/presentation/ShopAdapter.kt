package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.entity.ShopItem

class ShopAdapter : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>(){
    var shopList = listOf<ShopItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onShopItemLongListener : ((ShopItem) -> Unit)? = null
    var onShopItemClick : ((ShopItem) -> Unit)? = null

    private var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        Log.d("ShopAdapter", "${++count}")
        val layout = when(viewType){
            VIEW_TYPE_DISABLE -> R.layout.item_shop_disable
            VIEW_TYPE_ENABLE -> R.layout.item_shop_enable
            else -> throw java.lang.RuntimeException("Unknow view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return ShopViewHolder(view)
    }

    override fun getItemCount(): Int = shopList.size

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shop = shopList[position]

        holder.name.text = shop.name
        holder.count.text = shop.count
        holder.itemView.setOnLongClickListener {
            onShopItemLongListener?.invoke(shop)
            true
        }

        holder.itemView.setOnClickListener{
            onShopItemClick?.invoke(shop)
            Log.d("INFO_ITEM_SHOP:", "Name: ${shop.name}")
        }
    }

    //TODO исправление бага
    override fun onViewRecycled(holder: ShopViewHolder) {
        super.onViewRecycled(holder)
        holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if(item.enable){
            VIEW_TYPE_ENABLE
        } else{
            VIEW_TYPE_DISABLE
        }
    }

    class ShopViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val name : TextView = view.findViewById(R.id.tv_name)
        val count : TextView = view.findViewById(R.id.tv_count)
    }

    companion object{
        const val VIEW_TYPE_ENABLE = 100
        const val VIEW_TYPE_DISABLE = 101
        const val MAX_POOL_SIZE = 15
    }
}