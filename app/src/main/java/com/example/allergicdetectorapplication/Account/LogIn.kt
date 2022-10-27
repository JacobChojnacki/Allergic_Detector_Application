package com.example.allergicdetectorapplication.Account

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.UserTools.UserMain
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    private lateinit var edxEmailLog: EditText
    private lateinit var edxPasswordLog: EditText
    private lateinit var btnLogInLog: Button
    private lateinit var btnCancelLog: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        mAuth = FirebaseAuth.getInstance()

        edxEmailLog = findViewById(R.id.edxEmailLog)
        edxPasswordLog = findViewById(R.id.edxPasswordLog)
        btnLogInLog = findViewById(R.id.btnLogInLog)
        btnCancelLog = findViewById(R.id.btnCancelLog)

        btnCancelLog.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnLogInLog.setOnClickListener {
            val email = edxEmailLog.text.toString()
            val password = edxPasswordLog.text.toString()

            login(email, password)
        }
    }
    private fun login(email: String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@LogIn, UserMain::class.java).also {
                        it.putExtra("email", email.replaceAfter('@', "")
                                                        .replace("@","")
                                                        .uppercase())
                    }
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LogIn,
                                    "Wrong Email or Password",
                                         Toast.LENGTH_SHORT).show()
                }
            }
    }
}