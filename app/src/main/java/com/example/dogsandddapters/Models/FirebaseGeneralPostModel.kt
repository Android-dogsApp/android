package com.example.dogsandddapters.Models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.ktx.Firebase

class FirebaseGeneralPostModel {

    private val db = Firebase.firestore

    companion object {
        const val GENERALPOST_COLLECTION_PATH = "generalPosts"
    }

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
        }
        db.firestoreSettings = settings
    }


    fun getAllGeneralPosts(since: Long, callback: (List<GeneralPost>) -> Unit) {

        db.collection(GENERALPOST_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(GeneralPost.LAST_UPDATED, Timestamp(since, 0))
            .get().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val generalPosts: MutableList<GeneralPost> = mutableListOf()
                        for (json in it.result) {
                            val generalPost = GeneralPost.fromJSON(json.data)
                            generalPosts.add(generalPost)
                        }
                        callback(generalPosts)
                    }
                    false -> callback(listOf())
                }
            }
    }

    fun addGeneralPost(generalPost: GeneralPost, callback: () -> Unit) {
        db.collection(GENERALPOST_COLLECTION_PATH).document(generalPost.postid).set(generalPost.toJson).addOnSuccessListener {
            callback()
        }
    }
}