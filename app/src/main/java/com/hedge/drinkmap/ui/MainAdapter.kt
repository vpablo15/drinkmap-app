package com.hedge.drinkmap.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hedge.drinkmap.R
import com.hedge.drinkmap.data.models.Drink

class MainAdapter (private val context: Context,private val list:List<Drink>
        ,private val itemClickListener:OnDrinkClikListener)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    interface OnDrinkClikListener{
        fun onDrinkClick(drink: Drink)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardViewDrink = itemView.findViewById<CardView>(R.id.cardViewDrink)
        val imgDrink = itemView.findViewById<ImageView>(R.id.imgDrink)
        val textViewDrink = itemView.findViewById<TextView>(R.id.textViewNameDrink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        val item = list[position]
        //using glide for the image
        Glide.with(context)
            .load(item.image)
            .centerCrop()
            .into(holder.imgDrink)
        holder.textViewDrink.text = item.name
        holder.cardViewDrink.setOnClickListener { itemClickListener.onDrinkClick(item) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}