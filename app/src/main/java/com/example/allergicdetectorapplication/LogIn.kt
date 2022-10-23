package com.example.allergicdetectorapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LogIn : AppCompatActivity() {
    private lateinit var edxEmailLog: EditText
    private lateinit var edxPasswordLog: EditText
    private lateinit var btnLogInLog: Button
    private lateinit var btnCancelLog: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        edxEmailLog = findViewById(R.id.edxEmailLog)
        edxPasswordLog = findViewById(R.id.edxPasswordLog)
        btnLogInLog = findViewById(R.id.btnLogInLog)
        btnCancelLog = findViewById(R.id.btnCancelLog)

        btnCancelLog.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}