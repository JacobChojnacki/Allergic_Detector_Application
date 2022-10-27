package com.example.allergicdetectorapplication.UserTools

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allergicdetectorapplication.R


class UserAllergens : AppCompatActivity() {

    private lateinit var edxAdd_allergen: EditText
    private lateinit var btn_addAllergen: Button
    private lateinit var btn_CancelAllergen: Button

    var kindOfAllergens = arrayOf(
        "Orzeszki", "Pomidor", "Jaja", "Gluten",
        "Laktoza", "Seler"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allergens)

        btn_CancelAllergen = findViewById(R.id.btn_CancelAllergen)
        btn_addAllergen = findViewById(R.id.btn_addAllergen)
        edxAdd_allergen = findViewById(R.id.edxAdd_allergen)

        btn_CancelAllergen.setOnClickListener {
            val intent = Intent(this, UserMain::class.java)
            startActivity(intent)
        }

        val lvAllergens = findViewById<ListView>(R.id.lvAllergens)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, kindOfAllergens)
        lvAllergens.adapter = adapter
        lvAllergens.onItemClickListener =
            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                Toast.makeText(
                    applicationContext,
                    "clicked item = " + kindOfAllergens[p2],
                    Toast.LENGTH_SHORT
                ).show()
            }

//        btn_addAllergen.setOnClickListener {
//
//        }
    }
}