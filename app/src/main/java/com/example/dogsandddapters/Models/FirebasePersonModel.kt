package com.example.dogsandddapters.Models

import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.ktx.Firebase

class FirebasePersonModelModel {

    private val db = Firebase.firestore

    companion object {
        const val STUDENTS_COLLECTION_PATH = "students"
    }

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
//            setLocalCacheSettings(persistentCacheSettings {  })
        }
        db.firestoreSettings = settings
    }


    fun getAllStudents(callback: (List<Student>) -> Unit) {
        db.collection(STUDENTS_COLLECTION_PATH).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val students: MutableList<Student> = mutableListOf()
                    for (json in it.result) {
                        val student = Student.fromJSON(json.data)
                        students.add(student)
                    }
                    callback(students)
                }
                false -> callback(listOf())
            }
        }
    }

    fun addStudent(student: Student, callback: () -> Unit) {
        db.collection(STUDENTS_COLLECTION_PATH).document(student.id).set(student.json).addOnSuccessListener {
            callback()
        }
    }
}