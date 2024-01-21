package com.example.dogsandddapters.Models

import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.dogsandddapters.dao.AppLocalDatabase
import java.util.concurrent.Executors

class PersonPostModel private constructor() {

  //  private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = FirebaseModel()

    companion object {
        val instance: PersonPostModel = PersonPostModel()
    }

    interface GetAllPersonsListener {
        fun onComplete(personPosts: List<PersonPost>)
    }

    fun getAllPersonPost(callback: (List<PersonPost>) -> Unit) {
      //  firebaseModel.getAllPersonPost(callback)
        executor.execute {

            Thread.sleep(5000)

           // val personposts = database.personpostDao().getAll()
            mainHandler.post {
                // Main Thread
             //   callback(personposts)
            }
        }
    }

    fun addStudent(personpost: PersonPost, callback: () -> Unit) {
      //  firebaseModel.addPersonPost(PersonPost, callback)
        executor.execute {
      //      database.personpostDao().insert(personpost)
            mainHandler.post {
                callback()
            }
        }
    }
}