package com.example.dogsandddapters.Models

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class FirebasePersonPostModel {
    companion object {
        const val PERSONPOST_COLLECTION_PATH = "personPosts"
        const val GENERALPOST_COLLECTION_PATH = "generalPosts"
    }
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        db.firestoreSettings = settings
    }


    fun getAllPersonPosts(since: Long,publisher: String, callback: (List<PersonPost>) -> Unit){
        Log.i("getAllPersonPosts", "getAllPersonPosts -publisher ${publisher}")
        db.collection(PERSONPOST_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(PersonPost.LAST_UPDATED, Timestamp(since, 0))
            // .whereEqualTo("postpublisher", publisher)
            .get().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val personPosts: MutableList<PersonPost> = mutableListOf()
                        for (json in it.result) {
                            val personPost = PersonPost.fromJSON(json.data)
                            if (personPost != null) {
                                personPosts.add(personPost)
                            }

                        }
                        callback(personPosts)
                    }
                    false -> callback(listOf())
                }
            }
    }

    fun addPersonPost(personpost: PersonPost, callback: () -> Unit) {
        db.collection(PERSONPOST_COLLECTION_PATH).document(personpost.postid).set(personpost.json).addOnSuccessListener {
            callback()
        }
    }



    fun getPersonPostById(postid: String, callback: (PersonPost?) -> Unit) {
        db.collection(PERSONPOST_COLLECTION_PATH).document(postid).get().addOnSuccessListener { documentSnapshot ->
            val data = documentSnapshot.data
            if (data != null) {
                val personPost = PersonPost.fromJSON(data as Map<String, Any>)
                callback(personPost)
            } else {
                callback(null)
            }
        }
    }

    fun updatePersonPost(personpost: PersonPost, callback: () -> Unit) {
        db.collection(PERSONPOST_COLLECTION_PATH).document(personpost.postid).update(personpost.json).addOnSuccessListener {
            callback()
        }
    }



    fun deletePersonPost(id: String, callback: () -> Unit) {
        db.collection(PERSONPOST_COLLECTION_PATH).document(id).delete().addOnSuccessListener {
            callback()

        }
    }
}