package com.example.allergicdetectorapplication.UserTools

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.models.Allergens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserAllergens : AppCompatActivity() {

    private lateinit var btn_addAllergen: Button
    private lateinit var btn_CancelAllergen: Button
    private lateinit var dbRef: DatabaseReference
    private lateinit var userAllergensRecyclerView: RecyclerView
    private lateinit var userAllergensArrayList: ArrayList<Allergens>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allergens)

        btn_CancelAllergen = findViewById(R.id.btn_CancelAllergen)
        btn_addAllergen = findViewById(R.id.btn_addAllergen)
        // Recycle view
        userAllergensRecyclerView = findViewById(R.id.allergenList)
        userAllergensRecyclerView.layoutManager = LinearLayoutManager(this)
        userAllergensRecyclerView.setHasFixedSize(true)

        userAllergensArrayList = arrayListOf<Allergens>()

        getUserData()

        btn_addAllergen.setOnClickListener {
            val intent = Intent(this, AddAllergens::class.java)
            startActivity(intent)
        }

        btn_CancelAllergen.setOnClickListener {
            val intent = Intent(this, UserMain::class.java)
            startActivity(intent)
        }
    }

    private fun getUserData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Allergens")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (allergenSnapshot in snapshot.children) {
                        val allergenName = allergenSnapshot.getValue(Allergens::class.java)
                        userAllergensArrayList.add(allergenName!!)
                    }
                    userAllergensRecyclerView.adapter = MyAdapter(userAllergensArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}