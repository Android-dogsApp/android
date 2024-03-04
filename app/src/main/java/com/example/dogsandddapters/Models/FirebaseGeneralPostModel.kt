package com.example.dogsandddapters.Models

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class FirebaseGeneralPostModel {


    companion object {
        const val GENERALPOST_COLLECTION_PATH = "generalPosts"
    }

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        db.firestoreSettings = settings
    }


    fun getAllGeneralPosts(since: Long, callback: (List<GeneralPost>) -> Unit) {

        db.collection(GENERALPOST_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(GeneralPost.LAST_UPDATED, Timestamp(since, 0))
            .get().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val generalPosts: MutableList<GeneralPost> = mutableListOf()
                        Log.i("TAG", "result: ${it.result.size()}")
                        for (json in it.result) {
                            val generalPost = GeneralPost.fromJSON(json.data)
                            generalPosts.add(generalPost)
                        }
                        Log.i("TAG", "generalPosts size: ${generalPosts. size}")
                        callback(generalPosts)
                    }
                    false -> callback(listOf())
                }
            }
    }

    fun addGeneralPost(generalPost: GeneralPost, callback: () -> Unit) {
        db.collection(GENERALPOST_COLLECTION_PATH).document(generalPost.postid).set(generalPost.Json).addOnSuccessListener {
            callback()
        }
    }

//    fun getGeneralPostById(postid: String, callback: (GeneralPost?) -> Unit) {
//        db.collection(GENERALPOST_COLLECTION_PATH).document(postid).get().addOnSuccessListener {
//            val generalPost = GeneralPost.fromJSON(it.data)
//            callback(generalPost)
//        }
//    }

    fun getGeneralPostById(postid: String, callback: (GeneralPost?) -> Unit) {
        db.collection(GENERALPOST_COLLECTION_PATH).document(postid).get().addOnSuccessListener { documentSnapshot ->
            val data = documentSnapshot.data
            if (data != null) {
                val generalPost = GeneralPost.fromJSON(data as Map<String, Any>)
                callback(generalPost)
            } else {
                callback(null)
            }
        }
    }


    fun updateGeneralPost(generalPost: GeneralPost, callback: () -> Unit) {
        db.collection(GENERALPOST_COLLECTION_PATH).document(generalPost.postid).update(generalPost.Json).addOnSuccessListener {
            callback()
        }

    }

    fun deleteGeneralPost(id: String, callback: () -> Unit) {
        db.collection(GENERALPOST_COLLECTION_PATH).document(id).delete().addOnSuccessListener {
            callback()
        }
    }


}


