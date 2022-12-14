package com.example.allergicdetectorapplication.models

import com.google.firebase.database.Exclude
import java.util.HashMap
import java.util.UUID

data class Allergens(
    var allergenName: String? = null,
    var allergenName1: String? = null,
    var allergenName2: String? = null,
    var allergenName3: String? = null,
    var allergenName4: String? = null,
)

data class Users(
    var uuid: String? = null,
    var email: String? = null,
    var password: String? = null,
    var name: String? = null,
    var surname: String? = null,
    val city: String? = null,
    val allergens: List<Allergens>? = null
)

data class AllergenItem(
    val allergenName: String? = null
) {
    @Exclude
    fun getMap(): Map<String, Any?> {
        return mapOf(
            "allergenName" to allergenName
        )
    }
}

data class UserAllergens(
    var allergens: List<AllergenItem>? = null
)