package com.yonggeun.recyclerview.data

class AnimalRepository {

    private val animals = mutableListOf<Animal>()

    init {
        setAnimalData()
    }

    fun getAnimalData(): MutableList<Animal>{
        return animals
    }

    private fun setAnimalData(){
        animals.add(Animal("Cat", "나비", "010-0000-0000"))
        animals.add(Animal("Cat", "나나", "010-0000-0001"))
        animals.add(Animal("Cat", "냐옹", "010-0000-0002"))
        animals.add(Animal("Cat", "별이", "010-0000-0003"))
        animals.add(Animal("Dog", "봄이", "010-0000-0004"))
    }

    fun add(animal: Animal): MutableList<Animal>{
        animals.add(animal)
        return animals
    }

    fun remove(animal: Animal): MutableList<Animal>{
        animals.remove(animal)
        return animals
    }


}