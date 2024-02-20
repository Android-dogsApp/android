package com.example.dogsandddapters.Models

import com.example.dogsandddapters.dao.AppLocalDatabasePerson
import java.util.concurrent.Executors


class PersonModel private constructor() {

    private val database = AppLocalDatabasePerson.db
    private var executor = Executors.newSingleThreadExecutor()
    private val firebasePersonModel = FirebasePersonModel()

    companion object {
        val instance: PersonModel = PersonModel()
    }

//    fun getPerson(id: String, callback: () -> Unit) {
//        val person = firebasePersonModel.getPerson(id){
//            callback()
//        }
//        return person;
//    }
fun getPerson(id: String, callback: (Person?) -> Unit) {
    firebasePersonModel.getPerson(id) { person ->
        callback(person)
    }
}


    fun addPerson(person: Person, callback: () -> Unit) {
        firebasePersonModel.addPerson(person) {
            callback()
        }
    }

    fun updatePerson(person: Person, callback: () -> Unit) {
        firebasePersonModel.updatePerson(person) {
            callback()
        }
    }
}