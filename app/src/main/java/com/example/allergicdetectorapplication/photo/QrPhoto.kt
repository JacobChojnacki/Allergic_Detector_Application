package com.example.allergicdetectorapplication.photo

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R

class QrPhoto : AppCompatActivity() {

    private lateinit var btnConfirmQrPhoto: Button
    private lateinit var btnCancelQrPhoto: Button
    private lateinit var btnDoQrPhoto: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        btnDoQrPhoto = findViewById(R.id.btnTakeQrPhoto)
        btnCancelQrPhoto = findViewById(R.id.btnCancelQrPhoto)
        btnConfirmQrPhoto = findViewById(R.id.btnConfirmQrPhoto)


    }
}