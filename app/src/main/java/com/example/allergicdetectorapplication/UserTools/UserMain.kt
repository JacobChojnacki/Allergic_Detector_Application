package com.example.allergicdetectorapplication.UserTools

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.Account.MainActivity
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.databinding.ActivityUserMainBinding
import com.example.allergicdetectorapplication.models.Allergens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserMain : AppCompatActivity() {
    private lateinit var username: TextView
    private lateinit var btnCheckAllergens: Button
    private lateinit var btnCheckProduct: Button
    private lateinit var btnCalendarDust: Button
    private lateinit var btnLogOut: Button
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityUserMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_user_main)
//        val userInfo = intent.getStringExtra("email")
//        username = findViewById<TextView>(R.id.username).apply {
//            text = userInfo
//        }
        btnCheckAllergens = findViewById(R.id.btnAllergens)
        btnCheckProduct = findViewById(R.id.btnCheckProduct)
        btnCalendarDust = findViewById(R.id.btnCalendarDust)
        btnLogOut = findViewById(R.id.btnLogOut)

        readData(FirebaseAuth.getInstance().uid)


        btnCheckAllergens.setOnClickListener {
            val intent = Intent(this, UserAllergens::class.java)
            startActivity(intent)
        }

        btnCheckProduct.setOnClickListener {
            val intent = Intent(this, CheckProduct::class.java)
            startActivity(intent)
        }

        btnLogOut.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun readData(uid: String?) {

        database = FirebaseDatabase.getInstance().getReference("Users")
        if (uid != null) {
            database.child(uid).get().addOnSuccessListener {
                if (it.exists()) {
                    val user = it.child("surname").value
                    username = findViewById<TextView?>(R.id.username).apply {
                        text = user.toString()
                    }
                } else {
                    Toast.makeText(this, "Uzytkownk nie istnieje", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Serwer padl", Toast.LENGTH_SHORT).show()
            }
        }
    }


}