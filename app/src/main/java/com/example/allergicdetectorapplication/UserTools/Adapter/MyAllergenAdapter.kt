package com.example.allergicdetectorapplication.UserTools.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.UserTools.MyAdapter
import com.example.allergicdetectorapplication.models.AllergenItem

class MyAllergenAdapter : RecyclerView.Adapter<MyAllergenAdapter.MyViewHolder>() {

    private val allergenPuaList = ArrayList<AllergenItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.allergen_item_pua,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allergenPuaList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = allergenPuaList[position]

        holder.allergenName.text = currentItem.allergenName
    }


    fun updateAllergenList(allergenPuaList: List<AllergenItem>) {
        this.allergenPuaList.clear()
        this.allergenPuaList.addAll(allergenPuaList)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val allergenName: TextView = itemView.findViewById(R.id.AllergenPUA_Item)
        val onClickDelete: ((AllergenItem) -> AllergenItem)? = null
    }
}