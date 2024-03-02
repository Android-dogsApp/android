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

//    fun getPerson(since: Long,id: String, callback: (Person?) -> Unit) {
//        db.collection(PERSONS_COLLECTION_PATH)
//            .whereEqualTo("id", id)
//            .whereGreaterThanOrEqualTo(Person.LAST_UPDATED, Timestamp(since, 0))
//            .get().addOnCompleteListener {
//                when (it.isSuccessful) {
//                    true -> {
//                        val person = it.result.documents[0].toObject(Person::class.java)
//                        Log.i("TAG", "Person id:  $person.id")
//                        callback(person)
//                    }
//                    false -> callback(null)
//                }
//            }
//
//    }

//    fun getPerson(since: Long, id: String, callback: (Person?) -> Unit) {
//        db.collection(PERSONS_COLLECTION_PATH)
//            .whereEqualTo("id", id)
//            .whereGreaterThanOrEqualTo(Person.LAST_UPDATED, Timestamp(since, 0))
//            .get().addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val documents = task.result?.documents
//                    if (documents != null && documents.isNotEmpty()) {
//                        val person = documents[0].toObject(Person::class.java)
//                        Log.i("TAG", "Person id: ${person?.id}")
//                        callback(person)
//                    } else {
//                        Log.i("TAG", "No matching documents found.")
//                        callback(null)
//                    }
//                } else {
//                    Log.e("TAG", "Error getting documents: ${task.exception}")
//                    callback(null)
//                }
//            }
//    }




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