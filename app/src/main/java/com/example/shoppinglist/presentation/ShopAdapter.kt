package com.example.shoppinglist.presentation

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_enable, parent, false)

        return ShopViewHolder(view)
    }

    override fun getItemCount(): Int = shopList.size

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shop = shopList[position]
        val status = if(shop.enable){
            "Active"
        } else{
            "Not active"
        }
        holder.name.text = "${shop.name} $status"
        holder.count.text = shop.count
        holder.itemView.setOnLongClickListener {
            true
        }
        if (shop.enable){
            holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.purple_500))
        }
    }

    //TODO исправление бага
    override fun onViewRecycled(holder: ShopViewHolder) {
        super.onViewRecycled(holder)
        holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
    }

    class ShopViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val name : TextView = view.findViewById(R.id.tv_name)
        val count : TextView = view.findViewById(R.id.tv_count)
    }
}