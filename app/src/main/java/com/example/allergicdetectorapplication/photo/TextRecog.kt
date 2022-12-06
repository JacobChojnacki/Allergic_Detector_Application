package com.example.allergicdetectorapplication.photo

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.UserTools.CheckProduct
import com.example.allergicdetectorapplication.databinding.ActivityTextRecogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.lang.Exception
import java.util.Locale

class TextRecog : AppCompatActivity() {

    private lateinit var tvTextResult: TextView
    private lateinit var ivTextRec: ImageView
    private lateinit var btn_close_text_rec: Button
    private lateinit var btnTakeTextPhoto: ImageButton
    private lateinit var btnConfirmTakeTextPhoto: ImageButton
    private lateinit var binding: ActivityTextRecogBinding
    private val REQUEST_IMAGE_CAPTURE = 1
    private var imageBitmap: Bitmap? = null
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_recog)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_text_recog)

//        Firebase Connection
        database = FirebaseDatabase.getInstance().getReference("Users")
        mAuth = FirebaseAuth.getInstance()
        val allergensUser: ArrayList<String> = ArrayList()

        // Main User List
        val checkUserList: ArrayList<String> = ArrayList()

        galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val data = result?.data
            if (data != null) {
                imageBitmap =
                    InputImage.fromFilePath(this@TextRecog, data.data!!).bitmapInternal as Bitmap
                binding.ivTextRec.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.loading, null
                    )
                )
                processImage(checkUserList)
            }
        }

        database.child(mAuth.currentUser!!.uid).child("allergens").get()
            .addOnCompleteListener {
                allergensUser.add(it.result.value.toString())
                for (ingredient in allergensUser[0].split(",")) {
                    checkUserList.add(
                        ingredient
                            .replaceBefore("Name=", "")
                            .replace("Name=", "")
                            .replace("}", "")
                    )
                }
            }
        binding.apply {

            btnTakeTextPhoto.setOnClickListener {
                val options = arrayOf("KAMERA", "GALERIA")

                val builder = AlertDialog.Builder(this@TextRecog)
                builder.setTitle("DOKONAJ WYBORU")

                builder.setItems(options) { _, which ->
                    if (which == 0) {
                        takeImage()
                    } else {
                        val storageIntent = Intent()
                        storageIntent.type = "image/*"
                        storageIntent.action = Intent.ACTION_GET_CONTENT
                        galleryLauncher.launch(storageIntent)
                    }
                }
                builder.show()
            }
            btnConfirmTakeTextRec.setOnClickListener {
                binding.ivTextRec.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.loading, null
                    )
                )
                processImage(checkUserList)
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

    @SuppressLint("SetTextI18n")
    private fun processImage(checkUserList: ArrayList<String>) {
        val items: ArrayList<String> = ArrayList()
        if (imageBitmap != null) {
            val image = imageBitmap?.let {
                InputImage.fromBitmap(it, 0)
            }
            image?.let {
                recognizer.process(it)
                    .addOnSuccessListener {
                        items.add(
                            it.text.lowercase(Locale.ROOT).split("\n").toString()
                                .replace("[", "")
                                .replace("]", "")
                                .replace(" ", "")
                        )
                        binding.tvTextResult.text = it.text

                        if (checkUserList.any(items[0].split(",")::contains)) {
                            binding.ivTextRec.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.danger,
                                    null
                                )
                            )
                            binding.ivTextRec.background = null
                            binding.tvTextResult.text = "W TWOIM PRODUKCIE ZNAJDUJE/JĄ SIĘ: " +
                                    checkUserList.intersect(items[0].split(",").toSet()).toString()
                                        .replace("[", "")
                                        .replace("]", "")
                        } else {
                            binding.tvTextResult.text = ""
                            binding.ivTextRec.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.safe,
                                    null
                                )
                            )
                            binding.ivTextRec.background = null
                        }
                        Log.d("Results", items[0].split(",").toString())
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "SPRÓBUJ PONOWNIE", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            Toast.makeText(this, "NIE WYBRANO ZDJĘCIA", Toast.LENGTH_SHORT).show()
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
