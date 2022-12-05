package com.example.allergicdetectorapplication.photo


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.UserTools.CheckProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.lang.Exception

class QrPhoto : AppCompatActivity() {

    private lateinit var btnConfirmQrPhoto: Button
    private lateinit var btnCancelQrPhoto: Button
    private lateinit var btnScanBarcode: ImageButton
    private lateinit var ivQrCode: ImageView
    private lateinit var tvResult: TextView
    private lateinit var inputImage: InputImage
    private lateinit var barcodeScanner: BarcodeScanner
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val REQUEST_IMAGE_CAPTURE = 1
    private val TAG = "MyTag"
    private val CAMERA_PERMISSION_CODE = 123
    private val READ_STORAGE_PERMISSION_CODE = 123
    private val WRITE_STORAGE_PERMISSION_CODE = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        btnScanBarcode = findViewById(R.id.btnTakeQrPhoto)
        btnCancelQrPhoto = findViewById(R.id.btnCancelQrPhoto)
        btnConfirmQrPhoto = findViewById(R.id.btnConfirmQrPhoto)
        ivQrCode = findViewById(R.id.ivQrCode)
        tvResult = findViewById(R.id.tvResult)
        barcodeScanner = BarcodeScanning.getClient()

        database = FirebaseDatabase.getInstance().getReference("Users")
        mAuth = FirebaseAuth.getInstance()
        val allergensUser: ArrayList<String> = ArrayList()

        // Main User List
        val checkUserList: ArrayList<String> = ArrayList()

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


        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val data = result?.data
            try {
                val photo = data?.extras?.get("data") as Bitmap
                inputImage = InputImage.fromBitmap(photo, 0)
                processQr(checkUserList)
            } catch (e: Exception) {
                Log.d(TAG, "onActivityResult: " + e.message)
            }
        }

        galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val data = result?.data
            inputImage = InputImage.fromFilePath(this@QrPhoto, data?.data!!)
            processQr(checkUserList)
        }

        btnCancelQrPhoto.setOnClickListener {
            val intent = Intent(this, CheckProduct::class.java)
            startActivity(intent)
        }

        btnScanBarcode.setOnClickListener {
            val options = arrayOf("camera", "gallery")

            val builder = AlertDialog.Builder(this@QrPhoto)
            builder.setTitle("Pick a option")

            builder.setItems(options, DialogInterface.OnClickListener { dialog, which ->
                if (which == 0) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraLauncher.launch(cameraIntent)
                } else {
                    val storageIntent = Intent()
                    storageIntent.type = "image/*"
                    storageIntent.action = Intent.ACTION_GET_CONTENT
                    galleryLauncher.launch(storageIntent)
                }
            })
            builder.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun processQr(checkUserList: ArrayList<String>) {
        ivQrCode.visibility = View.GONE
        tvResult.visibility = View.VISIBLE
        barcodeScanner.process(inputImage).addOnCompleteListener {
            for (barcode: Barcode in it.result) {
                when (barcode.valueType) {
                    Barcode.TYPE_WIFI -> {
                        val ssid = barcode.wifi!!.ssid
                        val password = barcode.wifi!!.password
                        val type = barcode.wifi!!.encryptionType
                        tvResult.text = "ssid $ssid \n password $password \n type $type"
                    }

                    Barcode.TYPE_URL -> {
                        val title = barcode.url!!.title
                        val url = barcode.url!!.url
                        tvResult.text = "title $title \n url $url"
                    }

                    Barcode.TYPE_TEXT -> {
                        val data = barcode.displayValue
                        if (data != null) {
                            if (checkUserList.any(data.split(",")::contains)) {
                                Log.d("ZobaczymyDobrze", data.split(",").toString())
                            } else {
                                Log.d("ZobaczymyZLE", data.split(",").toString())
                                Log.d("ZobaczymyDANE", checkUserList.toString())
                                Log.d("ZobaczymyZLE1", data.split(",")[0])
                                Log.d("ZobaczymyDane1", checkUserList[0])

                            }
                            tvResult.text = "$data"
                        } else {
                            tvResult.text = "NIE WYKRYTO KODU QR"
                        }
                    }
                }
            }
        }.addOnFailureListener {
            Log.d(TAG, "processQr: ${it.message}")
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermission(android.Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this@QrPhoto,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this@QrPhoto, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@QrPhoto, "Permission Granted already", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    READ_STORAGE_PERMISSION_CODE
                )
            } else {
                Toast.makeText(this@QrPhoto, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                checkPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    WRITE_STORAGE_PERMISSION_CODE
                )
            } else {
                Toast.makeText(this@QrPhoto, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == WRITE_STORAGE_PERMISSION_CODE) {
            if (!(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this@QrPhoto, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun takeImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: Exception) {

        }
    }
}
