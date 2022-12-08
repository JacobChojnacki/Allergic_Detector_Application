package com.example.allergicdetectorapplication.UserTools

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.databinding.ActivityUserMainBinding

class Region : AppCompatActivity() {
    private lateinit var btnCalendar1: Button
    private lateinit var btnCancel: Button
    private lateinit var binding: ActivityUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(R.layout.regions)

        btnCalendar1 = findViewById(R.id.btnCalendar1)
        btnCancel = findViewById(R.id.btnCancel)

        btnCalendar1.setOnClickListener {
            val intent = Intent(this, Calendar::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, UserMain::class.java)
            startActivity(intent)
        }
    }
}