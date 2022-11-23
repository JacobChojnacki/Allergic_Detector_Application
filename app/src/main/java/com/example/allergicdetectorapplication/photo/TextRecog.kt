package com.example.allergicdetectorapplication.photo

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.UserTools.CheckProduct
import com.example.allergicdetectorapplication.databinding.ActivityTextRecogBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.lang.Exception

class TextRecog : AppCompatActivity() {

    private lateinit var tvTextResult: TextView
    private lateinit var ivTextRec: ImageView
    private lateinit var btn_close_text_rec: Button
    private lateinit var btnTakeTextPhoto: ImageButton
    private lateinit var btnConfirmTakeTextPhoto: ImageButton
    private lateinit var binding: ActivityTextRecogBinding
    private val REQUEST_IMAGE_CAPTURE = 1
    private var imageBitmap: Bitmap? = null
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_recog)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_text_recog)

        binding.apply {

            btnTakeTextPhoto.setOnClickListener {
                takeImage()

                tvTextResult.text = ""
            }
            btnConfirmTakeTextRec.setOnClickListener {
                processImage()
            }
        }

        tvTextResult = findViewById(R.id.tvTextResult)
        ivTextRec = findViewById(R.id.ivTextRec)
        btn_close_text_rec = findViewById(R.id.btn_close_text_rec)

        btn_close_text_rec.setOnClickListener {
            val intent = Intent(this, CheckProduct::class.java)
            startActivity(intent)
        }
    }

    private fun processImage() {

        if (imageBitmap != null) {
            val image = imageBitmap?.let {
                InputImage.fromBitmap(it, 0)
            }
            image?.let {
                recognizer.process(it)
                    .addOnSuccessListener {
                        binding.tvTextResult.text = it.text
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Spróbuj ponownie", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        else {
            Toast.makeText(this, "Nie wybrano zdjecia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun takeImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: Exception) {

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val extras: Bundle? = data?.extras
            imageBitmap = extras?.get("data") as Bitmap

            if (imageBitmap != null) {
                binding.ivTextRec.setImageBitmap(imageBitmap)
                binding.ivTextRec.setBackgroundColor(204)
            }
        }
    }
}