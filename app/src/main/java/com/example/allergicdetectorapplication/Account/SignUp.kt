package com.example.allergicdetectorapplication.Account

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    private lateinit var edxEmailData: EditText
    private lateinit var edxPassword: EditText
    private lateinit var edxName: EditText
    private lateinit var edxSurname: EditText
    private lateinit var edxCity: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnCancel: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

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

        btnRegister.setOnClickListener {
            val email = edxEmailData.text.toString()
            val password = edxPassword.text.toString()

            signUp(email, password)
        }
    }

    private fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp,
                                     "Something went wrong",
                                          Toast.LENGTH_SHORT).show()
                }
            }
    }
}