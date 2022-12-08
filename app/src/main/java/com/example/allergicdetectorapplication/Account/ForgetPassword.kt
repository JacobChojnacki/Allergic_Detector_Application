package com.example.allergicdetectorapplication.Account

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R
import com.google.firebase.auth.FirebaseAuth

class ForgetPassword : AppCompatActivity() {

    private lateinit var edxResetPassword: EditText
    private lateinit var btnResetConfirm: Button
    private lateinit var btnResetCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        edxResetPassword = findViewById(R.id.edx_reset_password)
        btnResetConfirm = findViewById(R.id.btn_reset_confirm)
        btnResetCancel = findViewById(R.id.btn_reset_cancel)

        btnResetConfirm.setOnClickListener {
            val email: String = edxResetPassword.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                Toast.makeText(
                    this,
                    "Prosze wpisac swój adres email",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Wysłanie kodu resetującego zakończono sukcesem!",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

}