package com.example.allergicdetectorapplication.UserTools

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R


class UserAllergens : AppCompatActivity() {

    private lateinit var lvAllergens: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allergens)
        lvAllergens.findViewById<ListView>(R.id.lvAllergens)

    }
}