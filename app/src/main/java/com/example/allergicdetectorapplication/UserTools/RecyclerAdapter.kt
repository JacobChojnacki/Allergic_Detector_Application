package com.example.allergicdetectorapplication.UserTools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.allergicdetectorapplication.databinding.ItemPylekBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var items = emptyList<Pylek>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPylekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setNewItems(pylki: List<Pylek>) {
        items = pylki
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPylekBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pylek) {
            binding.name.text = item.name
            binding.level.text = getLevel(item.level)
        }

        private fun getLevel(level: Int): String {
            return when(level) {
                1 -> "+"
                2 -> "++"
                else -> "+++"
            }
        }
    }


}