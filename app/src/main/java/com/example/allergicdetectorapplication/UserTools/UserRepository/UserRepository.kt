package com.example.allergicdetectorapplication.UserTools.UserRepository

import androidx.lifecycle.MutableLiveData
import com.example.allergicdetectorapplication.models.AllergenItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class UserRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")

    @Volatile
    private var INSTANCE: UserRepository? = null

    fun getInstance(): UserRepository {

        return INSTANCE ?: synchronized(this) {

            val instance = UserRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadAllergens(userAllergenList: MutableLiveData<List<AllergenItem>>) {

        databaseReference.child(FirebaseAuth.getInstance().currentUser!!.uid).child("allergens").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {

                    val _userAllergenList: List<AllergenItem> =
                        snapshot.children.map { dataSnapshot ->
                            dataSnapshot.getValue(AllergenItem::class.java)!!
                        }
                    userAllergenList.postValue(_userAllergenList)
                } catch (_: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}