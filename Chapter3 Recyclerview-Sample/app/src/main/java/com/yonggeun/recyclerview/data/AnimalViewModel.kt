package com.yonggeun.recyclerview.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnimalViewModel(private val repository: AnimalRepository) : ViewModel() {


    private val animalData: MutableLiveData<MutableList<Animal>> =
        MutableLiveData<MutableList<Animal>>()

    init {
        animalData.value = getAnimals()
    }

    val animals: LiveData<MutableList<Animal>>
        get() = animalData


    private fun getAnimals(): MutableList<Animal> {
        return repository.getAnimalData()
    }

    fun add(animal: Animal){
        animalData.value = repository.add(animal)
    }

    fun remove(animal: Animal){
        animalData.value = repository.remove(animal)
    }

}