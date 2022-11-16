package com.example.allergicdetectorapplication.UserTools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allergicdetectorapplication.UserTools.UserRepository.UserRepository
import com.example.allergicdetectorapplication.models.AllergenItem

class AllergenViewModel : ViewModel() {
    private val repository: UserRepository
    private val _allAllergens = MutableLiveData<List<AllergenItem>>()
    val allAllergens: LiveData<List<AllergenItem>> = _allAllergens

    init {
        repository = UserRepository().getInstance()
        repository.loadAllergens(_allAllergens)
    }
}