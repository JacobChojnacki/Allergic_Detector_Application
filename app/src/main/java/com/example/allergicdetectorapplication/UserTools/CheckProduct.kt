package com.example.allergicdetectorapplication.UserTools

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.photo.QrPhoto
import com.example.allergicdetectorapplication.photo.Symptoms
import com.example.allergicdetectorapplication.photo.TextRecog
import com.google.firebase.internal.InternalTokenProvider

class CheckProduct : AppCompatActivity() {
    private lateinit var btnQrPhoto: Button
    private lateinit var btnIngredientPhoto: Button
    private lateinit var btnPossibleSymptoms: Button
    private lateinit var btnCancelCheck: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_product)

        btnQrPhoto = findViewById(R.id.btnQrPhoto)
        btnIngredientPhoto = findViewById(R.id.btnIngredientPhoto)
        btnPossibleSymptoms = findViewById(R.id.btnPossibleSymptoms)
        btnCancelCheck = findViewById(R.id.btnCancelCheck)

        btnCancelCheck.setOnClickListener {
            intent = Intent(this, UserMain::class.java)
            startActivity(intent)
        }

        btnQrPhoto.setOnClickListener {
            intent = Intent(this, QrPhoto::class.java)
            startActivity(intent)
        }

        btnPossibleSymptoms.setOnClickListener{
            intent = Intent(this, Symptoms::class.java)
            startActivity(intent)
        }

        btnIngredientPhoto.setOnClickListener {
            intent = Intent(this, TextRecog::class.java)
            startActivity(intent)
        }

    }
}