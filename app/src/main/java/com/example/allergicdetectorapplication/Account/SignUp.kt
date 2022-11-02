package com.example.allergicdetectorapplication.Account

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.databinding.ActivitySignUpBinding
import com.example.allergicdetectorapplication.models.Allergens
import com.example.allergicdetectorapplication.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edxEmailData: EditText
    private lateinit var edxPassword: EditText
    private lateinit var edxName: EditText
    private lateinit var edxSurname: EditText
    private lateinit var edxCity: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnCancel: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        edxEmailData = findViewById(R.id.edxEmailData)
        edxPassword = findViewById(R.id.edxPassword)
        edxName = findViewById(R.id.edxName)
        edxSurname = findViewById(R.id.edxSurname)
        edxCity = findViewById(R.id.edxCity)
        btnRegister = findViewById(R.id.btnRegister)
        btnCancel = findViewById(R.id.btnCancel)

        btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edxEmailData.text.toString()
            val password = binding.edxPassword.text.toString()
            val name = binding.edxName.text.toString()
            val surname = binding.edxSurname.text.toString()
            val city = binding.edxCity.text.toString()
            val allergens = mutableListOf(
                Allergens("orzeszki"),
                Allergens("pomidor"),
                Allergens("jaja"),
                Allergens("laktoza"),
                Allergens("seler"),
            )

            signUp(email, password)
            database = FirebaseDatabase.getInstance().getReference("Users")
            val user = Users(email, password, name, surname, city, allergens)
            FirebaseAuth.getInstance().uid?.let { it1 ->
                database.child(it1).setValue(user).addOnSuccessListener {
                    binding.edxName.text.clear()
                    binding.edxEmailData.text.clear()
                    binding.edxPassword.text.clear()
                    binding.edxSurname.text.clear()
                    binding.edxCity.text.clear()

                    Toast.makeText(this, "Udalo sie", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Nie udalo sie", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    private fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@SignUp,
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}