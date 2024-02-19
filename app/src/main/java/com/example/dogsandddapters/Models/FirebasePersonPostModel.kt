package com.example.dogsandddapters.Models
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.ktx.Firebase

class FirebasePersonPostModel {

    private val db = Firebase.firestore

    companion object {
        const val PERSONPOST_COLLECTION_PATH = "personposts"
    }

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
//            setLocalCacheSettings(persistentCacheSettings {  })
        }
        db.firestoreSettings = settings
    }


    fun getAllPersonPosts(since: Long, callback: (List<PersonPost>) -> Unit) {

        db.collection(PERSONPOST_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(PersonPost.LAST_UPDATED, Timestamp(since, 0))
            .get().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val personPosts: MutableList<PersonPost> = mutableListOf()
                        for (json in it.result) {
                            val personPost = PersonPost.fromJSON(json.data)
                            personPosts.add(personPost)
                        }
                        callback(personPosts)
                    }
                    false -> callback(listOf())
                }
            }
    }

    fun addPersonPost(personPost: PersonPost, callback: () -> Unit) {
        db.collection(PERSONPOST_COLLECTION_PATH).document(personPost.postid).set(personPost.toJson).addOnSuccessListener {
            callback()
        }
    }
}