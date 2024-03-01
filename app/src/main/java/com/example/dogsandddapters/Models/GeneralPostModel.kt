package com.example.dogsandddapters.Models

import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dogsandddapters.dao.AppLocalDatabaseGeneralPost
import java.util.concurrent.Executors

class GeneralPostModel private constructor() {


    enum class LoadingState {
        LOADING,
        LOADED
    }

    private val database = AppLocalDatabaseGeneralPost.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val FirebasePersonPostModel = FirebaseGeneralPostModel()
    private val generalPosts: LiveData<MutableList<GeneralPost>>? = null
    val generalPostsListLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)
    companion object {
        val instance: GeneralPostModel = GeneralPostModel()
    }

    interface GetAllPersonsListener {
        fun onComplete(generalPosts: List<GeneralPost>)
    }

    fun getAllgeneralPosts(): LiveData<MutableList<GeneralPost>> {
        refreshAllgeneralPosts()
        return generalPosts ?: database.GeneralPostDao().getAll()
    }

    fun refreshAllgeneralPosts() {

        generalPostsListLoadingState.value = LoadingState.LOADING

        // 1. Get last local update
        val lastUpdated: Long = PersonPost.lastUpdated

        // 2. Get all updated records from firestore since last update locally
        FirebasePersonPostModel.getAllGeneralPosts(lastUpdated) { list ->
            Log.i("TAG", "Firebase returned ${list.size}, lastUpdated: $lastUpdated")
            // 3. Insert new record to ROOM
            executor.execute {
                var time = lastUpdated
                for (personPost in list) {
                    database.GeneralPostDao().insert(personPost)

                    personPost.lastUpdated?.let {
                        if (time < it)
                            time = personPost.lastUpdated ?: System.currentTimeMillis()
                    }
                }

                // 4. Update local data
                PersonPost.lastUpdated = time
                generalPostsListLoadingState.postValue(LoadingState.LOADED)
            }
        }
    }

    fun addGeneralPost(generalPost: GeneralPost, callback: () -> Unit) {
        FirebasePersonPostModel.addGeneralPost(generalPost) {
            FirebasePersonPostModel()
            callback()
        }
    }
}
