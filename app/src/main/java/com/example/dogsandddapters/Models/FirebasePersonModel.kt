package com.example.dogsandddapters.Models

import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.ktx.Firebase

class FirebasePersonModelModel {

    private val db = Firebase.firestore

    companion object {
        const val PERSONS_COLLECTION_PATH = "persons"
    }

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
//            setLocalCacheSettings(persistentCacheSettings {  })
        }
        db.firestoreSettings = settings
    }


    fun getAllPersons(callback: (List<Person>) -> Unit) {
        db.collection(PERSONS_COLLECTION_PATH).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val persons: MutableList<Person> = mutableListOf()
                    for (json in it.result) {
                        val person = Person.fromJSON(json.data)
                        persons.add(person)
                    }
                    callback(persons)
                }
                false -> callback(listOf())
            }
        }
    }

    fun addPerson(person: Person, callback: () -> Unit) {
        db.collection(PERSONS_COLLECTION_PATH).document(person.id).set(person.json).addOnSuccessListener {
            callback()
        }
    }
}