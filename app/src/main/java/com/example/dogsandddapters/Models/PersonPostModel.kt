package com.example.dogsandddapters.Models

import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dogsandddapters.dao.AppLocalDatabasePersonPost
import java.util.concurrent.Executors

class PersonPostModel private constructor() {

    enum class LoadingState {
        LOADING,
        LOADED
    }

   private val database = AppLocalDatabasePersonPost.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val FirebasePersonPostModel = FirebasePersonPostModel()
    private val personPosts: LiveData<MutableList<PersonPost>>? = null
    val personPostsListLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)
    companion object {
        val instance: PersonPostModel = PersonPostModel()
    }

    interface GetAllPersonsListener {
        fun onComplete(personPosts: List<PersonPost>)
    }

    fun getAllpersonPosts(): LiveData<MutableList<PersonPost>> {
        refreshAllpersonPosts()
        return personPosts ?: database.PersonPostsDao().getAll()
    }

    fun refreshAllpersonPosts() {

        personPostsListLoadingState.value = LoadingState.LOADING

        // 1. Get last local update
        val lastUpdated: Long = PersonPost.lastUpdated

        // 2. Get all updated records from firestore since last update locally
        FirebasePersonPostModel.getAllPersonPosts(lastUpdated) { list ->
            Log.i("TAG", "Firebase PersonPost returned ${list.size}, lastUpdated: $lastUpdated")
            // 3. Insert new record to ROOM
            executor.execute {
                var time = lastUpdated
                for (personPost in list) {
                    database.PersonPostsDao().insert(personPost)

                    personPost.lastUpdated?.let {
                        if (time < it)
                            time = personPost.lastUpdated ?: System.currentTimeMillis()
                    }
                }

                // 4. Update local data
                PersonPost.lastUpdated = time
                personPostsListLoadingState.postValue(LoadingState.LOADED)
            }
        }
    }

    fun addPersonPost(personPost: PersonPost, callback: () -> Unit) {
        FirebasePersonPostModel.addPersonPost(personPost) {
            refreshAllpersonPosts()
            callback()
        }
    }
}
