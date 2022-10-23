package com.example.allergicdetectorapplication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SignUp : AppCompatActivity() {

    private lateinit var edxEmailData: EditText
    private lateinit var edxPassword: EditText
    private lateinit var edxName: EditText
    private lateinit var edxSurname: EditText
    private lateinit var edxCity: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

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
    }
}