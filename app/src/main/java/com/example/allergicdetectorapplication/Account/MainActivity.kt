package com.example.allergicdetectorapplication.Account

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.allergicdetectorapplication.R


class MainActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnSignUP: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btnLoginIn)
        btnSignUP = findViewById(R.id.btnSignUp)
        btnSignUP.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }
    }
}