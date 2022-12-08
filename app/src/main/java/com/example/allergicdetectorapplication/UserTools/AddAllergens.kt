package com.example.allergicdetectorapplication.UserTools

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_user_allergens)
        replaceFragment(Home())
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

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun updateAllergens() {
        val database = databaseReference.child(mAuth.currentUser!!.uid)
        val userData = AllergenItem(edxAddAllergens.text.toString())
        database.child("allergens").child(edxAddAllergens.text.toString().trim { it <= ' ' }).setValue(userData)
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    Toast.makeText(this, "DODAWANIE ZAKONCZONO SUKCESEM", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "WYSTAPIL PROBLEM, SPRÓBUJ PONOWNIE", Toast.LENGTH_SHORT).show()
                }
            }
    }
}