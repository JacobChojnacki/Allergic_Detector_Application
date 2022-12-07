package com.example.allergicdetectorapplication.photo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.UserTools.CheckProduct
import com.example.allergicdetectorapplication.databinding.ActivityTextRecogBinding

class Symptoms : AppCompatActivity() {

    private lateinit var btn_close_symptoms: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms)
        btn_close_symptoms = findViewById(R.id.btn_cancel_symptoms)
        btn_close_symptoms.setOnClickListener{
            val intent = Intent(this, CheckProduct::class.java)
            startActivity(intent)
        }

    }
}
