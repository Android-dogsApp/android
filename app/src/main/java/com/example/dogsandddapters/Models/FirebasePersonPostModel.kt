package com.example.dogsandddapters.Models

import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase


class FirebasePersonPostModelModel {

    private val db = Firebase.firestore

    companion object {
        const val PERSONPOSTS_COLLECTION_PATH = "personPosts"
    }

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
//            setLocalCacheSettings(persistentCacheSettings {  })
        }
        db.firestoreSettings = settings
    }


    fun getAllStudents(callback: (List<PersonPost>) -> Unit) {
        db.collection(PERSONPOSTS_COLLECTION_PATH).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val students: MutableList<PersonPost> = mutableListOf()
                    for (json in it.result) {
                        val personPost = PersonPost.fromJSON(json.data)
                       // persnPosts.add(personPost)
                    }
                    callback(students)
                }
                false -> callback(listOf())
            }
        }
    }

    fun addPersonPost(personpost: PersonPost, callback: () -> Unit) {
        db.collection(PERSONPOSTS_COLLECTION_PATH).document(addPersonPost().id).set(personpost.json).addOnSuccessListener {
            callback()
        }
    }
}