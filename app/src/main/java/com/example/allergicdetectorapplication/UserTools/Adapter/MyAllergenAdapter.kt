package com.example.allergicdetectorapplication.UserTools.Adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.models.AllergenItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MyAllergenAdapter : RecyclerView.Adapter<MyAllergenAdapter.MyViewHolder>() {

    private val allergenPuaList = ArrayList<AllergenItem>()
    private lateinit var mAuth: FirebaseAuth

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")
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
        mAuth = FirebaseAuth.getInstance()
        val database = databaseReference.child(mAuth.currentUser!!.uid)
        holder.allergenName.text = currentItem.allergenName
        holder.button.setOnClickListener{
            database.child("allergens").child(currentItem.allergenName!!).removeValue()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAllergenList(allergenPuaList: List<AllergenItem>) {
        this.allergenPuaList.clear()
        this.allergenPuaList.addAll(allergenPuaList)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val allergenName: TextView = itemView.findViewById<TextView>(R.id.AllergenPUA_Item)
        var button: ImageButton = itemView.findViewById<ImageButton>(R.id.btnDeleteAllergenPUA)
    }
}