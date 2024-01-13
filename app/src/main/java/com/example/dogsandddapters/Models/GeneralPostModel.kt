package com.example.dogsandddapters.Models

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.dogsandddapters.dao.AppLocalDatabase
import java.util.concurrent.Executors

class GeneralPostModel private constructor() {

    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val instance: GeneralPostModel = GeneralPostModel()
    }

    interface GetAllGeneralPostsListener {
        fun onComplete(generalposts: List<GeneralPost>)
    }

    fun getAllGeneralPosts(callback: (List<GeneralPost>) -> Unit) {
        executor.execute {

            Thread.sleep(5000)

            val students = database.GeneralPostDao().getAll()
            mainHandler.post {
                // Main Thread
                callback(students)
            }
        }
    }

    fun addStudent(generalpost: GeneralPost, callback: () -> Unit) {
        executor.execute {
            database.GeneralPostDao().insert(generalpost)
            mainHandler.post {
                callback()
            }
        }
    }
}