package com.example.dogsandddapters.Models

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class FirebasePersonModel {

//    private val db = Firebase.firestore

    companion object {
        const val PERSONS_COLLECTION_PATH = "persons"
    }

//    init {
//        val settings = firestoreSettings {
//            setLocalCacheSettings(memoryCacheSettings {  })
//        }
//        db.firestoreSettings = settings
//    }

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        db.firestoreSettings = settings
    }



//    fun getAllPersons(since: Long, callback: (List<Person>) -> Unit) {
//
//        db.collection(PERSONS_COLLECTION_PATH)
//            .whereGreaterThanOrEqualTo(Person.LAST_UPDATED, Timestamp(since, 0))
//            .get().addOnCompleteListener {
//                when (it.isSuccessful) {
//                    true -> {
//                        val persons: MutableList<Person> = mutableListOf()
//                        for (json in it.result) {
//                            val person = Person.fromJSON(json.data)
//                            persons.add(person)
//                        }
//                        callback(persons)
//                    }
//                    false -> callback(listOf())
//                }
//            }
//    }

//    fun getPerson(id: String, callback: () -> Unit) {
//        db.collection(PERSONS_COLLECTION_PATH).document(id).get().addOnCompleteListener {
//            when (it.isSuccessful) {
//                true -> {
//                    val person = it.result.toObject(Person::class.java)
//                    callback()
//                }
//                false -> callback()
//            }
//        }
//    }

    fun getPerson(id: String, callback: (Person?) -> Unit) {
        db.collection(PERSONS_COLLECTION_PATH).document(id).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val person = it.result.toObject(Person::class.java)
                    callback(person)
                }
                false -> callback(null)
            }
        }
    }





    fun addPerson(person: Person, callback: () -> Unit) {
        db.collection(PERSONS_COLLECTION_PATH).document(person.id).set(person.json).addOnSuccessListener {
            callback()
        }
    }

    fun updatePerson(person: Person, callback: () -> Unit) {
        db.collection(PERSONS_COLLECTION_PATH).document(person.id).update(person.json).addOnSuccessListener {
            callback()
        }
    }
}