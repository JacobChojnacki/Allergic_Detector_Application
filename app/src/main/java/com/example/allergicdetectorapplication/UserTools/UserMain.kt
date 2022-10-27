package com.example.allergicdetectorapplication.UserTools

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.Account.MainActivity
import com.example.allergicdetectorapplication.R

class UserMain : AppCompatActivity() {
    private lateinit var username: TextView
    private lateinit var btnCheckAllergens: Button
    private lateinit var btnCheckProduct: Button
    private lateinit var btnCalendarDust: Button
    private lateinit var btnLogOut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)

        val userInfo = intent.getStringExtra("email")

        username = findViewById<TextView>(R.id.username).apply {
            text = userInfo
        }
        btnCheckAllergens = findViewById(R.id.btnAllergens)
        btnCheckProduct = findViewById(R.id.btnCheckProduct)
        btnCalendarDust = findViewById(R.id.btnCalendarDust)
        btnLogOut = findViewById(R.id.btnLogOut)


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
}