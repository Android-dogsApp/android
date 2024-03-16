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
    private var executor2 = Executors.newSingleThreadExecutor()
    private var executor3 = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val FirebasePersonPostModel = FirebasePersonPostModel()
    private val personPosts: LiveData<MutableList<PersonPost>> = database.PersonPostsDao().getAll()
    val personPostsListLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)
    companion object {
        val instance: PersonPostModel = PersonPostModel()
    }


    interface GetAllPersonsListener {
        fun onComplete(personPosts: List<PersonPost>)
    }

    fun getAllpersonPosts(publisher: String): LiveData<MutableList<PersonPost>> {
        refreshAllpersonPosts(publisher)
        return personPosts
    }


//    fun getAllpersonPosts(callback: (List<PersonPost>) -> Unit): LiveData<MutableList<PersonPost>> {
//        // Assuming refreshAllpersonPosts updates personPosts internally
//        refreshAllpersonPosts()
//
//        // Pass the current value of personPosts to the callback
//        callback(personPosts.value.orEmpty())
//
//        return personPosts
//    }


    fun refreshAllpersonPosts(publisher: String)  {

        personPostsListLoadingState.value = LoadingState.LOADING

        // 1. Get last local update
        val lastUpdated: Long = PersonPost.lastUpdated

        // 2. Get all updated records from firestore since last update locally
        FirebasePersonPostModel.getAllPersonPosts(lastUpdated, publisher) { list ->
            Log.i("TAG", "Firebase PersonPost returned ${list.size}, lastUpdated: $lastUpdated")
            // 3. Insert new record to ROOM
            executor.execute {
                var time = lastUpdated
                for (personPost in list) {
                    database.PersonPostsDao().insert(personPost)


                    Log.i("TAG", "database.PersonPostsDao() ${database.PersonPostsDao().getAll().value?.size}")

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

    fun addPersonPost(publisher: String, personPost: PersonPost, callback: () -> Unit) {
        FirebasePersonPostModel.addPersonPost(personPost) {
            refreshAllpersonPosts(publisher)
            callback()

        }
    }

    fun getPersonPostById(id: String, callback: (PersonPost?) -> Unit) : LiveData<PersonPost>{
        FirebasePersonPostModel.getPersonPostById(id) {
            callback(it)
        }
        return database.PersonPostsDao().getPersonPostById(id)

    }

    fun updatePersonPost(personPost: PersonPost, callback: () -> Unit) {
        FirebasePersonPostModel.updatePersonPost(personPost) {
            executor2.execute {
                database.PersonPostsDao().updatePersonPost(personPost)
            }
            //refreshAllpersonPosts()
            callback()
        }

    }

    fun deletePersonPost(personpost: PersonPost, callback: () -> Unit) {
        FirebasePersonPostModel.deletePersonPost(personpost.postid) {
            executor3.execute {
                database.PersonPostsDao().delete(personpost)
            }
            callback()
        }
    }
}