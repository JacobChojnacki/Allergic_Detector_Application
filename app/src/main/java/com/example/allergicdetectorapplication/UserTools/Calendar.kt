package com.example.allergicdetectorapplication.UserTools

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R

class Calendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_calendar)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, Table())
            .commit()
    }
}