package com.yonggeun.recyclerview.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class AnimalViewModelFactory(private val repository: AnimalRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnimalViewModel(repository) as T
    }
}