package com.example.allergicdetectorapplication.UserTools

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.models.AllergenItem
import com.example.allergicdetectorapplication.models.Allergens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MyAdapter(private val allergensList: ArrayList<Allergens>) :


    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mAuth: FirebaseAuth

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.allergen_item,
            parent, false
        )

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allergensList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = allergensList[position]
        mAuth = FirebaseAuth.getInstance()
        val database = databaseReference.child(mAuth.currentUser!!.uid)
        val orzeszki = AllergenItem("orzeszki")
        val pomidor = AllergenItem("pomidor")
        val jaja = AllergenItem("jaja")
        val gluten = AllergenItem("gluten")
        val laktoza = AllergenItem("laktoza")
        holder.firstAllergen.text = currentItem.allergenName
        holder.secondAllergen.text = currentItem.allergenName1
        holder.thirdAllergen.text = currentItem.allergenName2
        holder.fourthAllergen.text = currentItem.allergenName3
        holder.fiveAllergen.text = currentItem.allergenName4
        database.child("allergens").child("orzeszki").get().addOnCompleteListener {
            holder.switch.isChecked = it.result.value.toString() == "{allergenName=orzeszki}"
        }
        database.child("allergens").child("pomidor").get().addOnCompleteListener {
            holder.switch2.isChecked = it.result.value.toString() == "{allergenName=pomidor}"
        }
        database.child("allergens").child("jaja").get().addOnCompleteListener {
            holder.switch3.isChecked = it.result.value.toString() == "{allergenName=jaja}"
        }
        database.child("allergens").child("gluten").get().addOnCompleteListener {
            holder.switch4.isChecked = it.result.value.toString() == "{allergenName=gluten}"
        }
        database.child("allergens").child("laktoza").get().addOnCompleteListener {
            holder.switch5.isChecked = it.result.value.toString() == "{allergenName=laktoza}"
        }
        holder.switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                database.child("allergens").child("orzeszki").setValue(orzeszki)
            } else {
                database.child("allergens").child("orzeszki").removeValue()
            }
        }
        holder.switch2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                database.child("allergens").child("pomidor").setValue(pomidor)
            } else {
                database.child("allergens").child("pomidor").removeValue()
            }
        }
        holder.switch3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                database.child("allergens").child("jaja").setValue(jaja)
            } else {
                database.child("allergens").child("jaja").removeValue()
            }
        }
        holder.switch4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                database.child("allergens").child("gluten").setValue(gluten)
            } else {
                database.child("allergens").child("gluten").removeValue()
            }
        }
        holder.switch5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                database.child("allergens").child("laktoza").setValue(laktoza)
            } else {
                database.child("allergens").child("laktoza").removeValue()
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstAllergen: TextView = itemView.findViewById(R.id.Allergen)
        val secondAllergen: TextView = itemView.findViewById(R.id.Allergen2)
        val thirdAllergen: TextView = itemView.findViewById(R.id.Allergen3)
        val fourthAllergen: TextView = itemView.findViewById(R.id.Allergen4)
        val fiveAllergen: TextView = itemView.findViewById(R.id.Allergen5)

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val switch: Switch = itemView.findViewById(R.id.AllergenSwitch)

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val switch2: Switch = itemView.findViewById(R.id.Allergen2Switch)

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val switch3: Switch = itemView.findViewById(R.id.Allergen3Switch)

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val switch4: Switch = itemView.findViewById(R.id.Allergen4Switch)

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val switch5: Switch = itemView.findViewById(R.id.Allergen5Switch)
    }
}