package com.example.allergicdetectorapplication.UserTools

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.models.Allergens

class MyAdapter(private val allegensList: ArrayList<Allergens>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.allergen_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allegensList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = allegensList[position]

        holder.firstAllergen.text = currentItem.allergenName
        holder.secondAllergen.text = currentItem.allergenName1
        holder.thirdAllergen.text = currentItem.allergenName2
        holder.fourthAllergen.text = currentItem.allergenName3
        holder.fiveAllergen.text = currentItem.allergenName4
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstAllergen: TextView = itemView.findViewById(R.id.Allergen)
        val secondAllergen: TextView = itemView.findViewById(R.id.Allergen2)
        val thirdAllergen: TextView = itemView.findViewById(R.id.Allergen3)
        val fourthAllergen: TextView = itemView.findViewById(R.id.Allergen4)
        val fiveAllergen: TextView = itemView.findViewById(R.id.Allergen5)
    }
}