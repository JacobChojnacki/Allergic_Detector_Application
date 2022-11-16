package com.example.allergicdetectorapplication.UserTools

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.models.AllergenItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddAllergens : AppCompatActivity() {
    private lateinit var btn_addAllergen: Button
    private lateinit var btn_cancelAllergen: Button
    private lateinit var edxAddAllergens: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_user_allergens)
        btn_cancelAllergen = findViewById(R.id.btn_cancelAllergen)
        btn_addAllergen = findViewById(R.id.btn_addAllergen)
        edxAddAllergens = findViewById(R.id.edxAddAllergen)
        mAuth = FirebaseAuth.getInstance()

        btn_addAllergen.setOnClickListener {
            updateAllergens()
        }

        btn_cancelAllergen.setOnClickListener {
            val intent = Intent(this, UserAllergens::class.java)
            startActivity(intent)
        }


    }

    private fun updateAllergens() {
        val database =
            FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
        val userData = AllergenItem(edxAddAllergens.text.toString())

        database.child("allergens").child(edxAddAllergens.text.toString()).setValue(userData)
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Git", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Zle", Toast.LENGTH_SHORT).show()
                }
            }
    }
}