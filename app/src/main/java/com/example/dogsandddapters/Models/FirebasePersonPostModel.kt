package com.example.dogsandddapters.Models

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
    fun getAllPersonPosts(since: Long, callback: (List<PersonPost>) -> Unit){
            db.collection(PERSONPOST_COLLECTION_PATH)
                .whereGreaterThanOrEqualTo(PersonPost.LAST_UPDATED, Timestamp(since, 0))
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
        //db.collection(GENERALPOST_COLLECTION_PATH).document(personpost.postid).set(personpost.json)
    }



}