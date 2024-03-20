package com.example.dogsandddapters.Models

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class FirebasePersonModel {



    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("users") // Adjust "users" to your Firestore collection name
    companion object {
        const val PERSONS_COLLECTION_PATH = "persons"
    }


    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        db.firestoreSettings = settings
    }


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

    fun getPersonById(Id: String, callback: (Person?) -> Unit) {
        db.collection(PERSONS_COLLECTION_PATH).document(Id).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val person = it.result.toObject(Person::class.java)
                    callback(person)
                }
                false -> callback(null)
            }
        }
    }

    fun getPersonByEmail(email: String, callback: (Person?) -> Unit) {
        db.collection(PERSONS_COLLECTION_PATH).whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents = task.result
                    if (documents != null && !documents.isEmpty) {
                        val document = documents.documents[0]
                        val person = document.toObject(Person::class.java)
                        Log.i("TAG", "Person id: ${person?.id}")
                        callback(person)
                    } else {
                        callback(null)
                        Log.i("TAG", "Person id: null2")
                    }
                } else {
                    // Handle failure
                    callback(null)
                }
            }
    }



    fun updatePerson(id:String ,person: Person, callback: () -> Unit) {
        db.collection(PERSONS_COLLECTION_PATH).document(id).update(person.json).addOnSuccessListener {
            callback()
            Log.i("FirebasePersonModel", "updatePerson: ${person.id}")
        }.addOnFailureListener { e ->
                // Handle failure, log error, or perform additional actions if needed
                Log.e("FirebasePersonModel", "Error updating person: ${e.message}", e)
            }
    }

}