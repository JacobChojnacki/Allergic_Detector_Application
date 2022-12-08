package com.example.allergicdetectorapplication.UserTools

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.databinding.ItemPylenieBinding

class NestedRecyclerAdapter : RecyclerView.Adapter<NestedRecyclerAdapter.ViewHolder>() {

    private var items = emptyList<Pylenie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPylenieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setNewItems(newItems: List<Pylenie>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPylenieBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(item: Pylenie) {
            with(binding) {
                if (item.period1) {
                    period1.setBackgroundColor(itemView.context.getColor(R.color.red))
                } else {
                    period1.setBackgroundColor(itemView.context.getColor(R.color.white))
                }
                if (item.period2) {
                    period2.setBackgroundColor(itemView.context.getColor(R.color.red))
                } else {
                    period2.setBackgroundColor(itemView.context.getColor(R.color.white))
                }
                if (item.period3) {
                    period3.setBackgroundColor(itemView.context.getColor(R.color.red))
                } else {
                    period3.setBackgroundColor(itemView.context.getColor(R.color.white))
                }
            }
        }
    }


}