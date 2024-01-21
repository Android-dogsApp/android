package com.example.dogsandddapters.Models
import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.dogsandddapters.dao.AppLocalDatabase
import java.util.concurrent.Executors


class PersonModel private constructor() {

 //   private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebasePersonModel = FirebasePersonModel()

    companion object {
        val instance: PersonModel = PersonModel()
    }

    interface GetAllPersonsListener {
        fun onComplete(students: List<Person>)
    }

    fun getAllPersons(callback: (List<Person>) -> Unit) {
        firebasePersonModel.getAllPersons(callback)
//        executor.execute {
//
//            Thread.sleep(5000)
//
//            val students = database.studentDao().getAll()
//            mainHandler.post {
//                // Main Thread
//                callback(students)
//            }
//        }
    }

    fun addStudent(person: Person, callback: () -> Unit) {
        firebasePersonModel.addPerson(person, callback)
//        executor.execute {
//            database.studentDao().insert(student)
//            mainHandler.post {
//                callback()
//            }
//        }
    }
}