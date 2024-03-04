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
    private var executor2 = Executors.newSingleThreadExecutor()
    private var executor3 = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val FirebaseGeneralPostModel = FirebaseGeneralPostModel()
    private val generalPosts: LiveData<MutableList<GeneralPost>> =database.GeneralPostDao().getAll()
    val generalPostsListLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)
    companion object {
        val instance: GeneralPostModel = GeneralPostModel()
    }

    interface GetAllPersonsListener {
        fun onComplete(generalPosts: List<GeneralPost>)
    }

    fun getAllgeneralPosts(): LiveData<MutableList<GeneralPost>> {
        refreshAllgeneralPosts()
        return generalPosts
    }

    fun refreshAllgeneralPosts() {

        generalPostsListLoadingState.value = LoadingState.LOADING

        // 1. Get last local update
        val lastUpdated: Long = GeneralPost.lastUpdated

        // 2. Get all updated records from firestore since last update locally
        FirebaseGeneralPostModel.getAllGeneralPosts(lastUpdated) { list ->
            Log.i("TAG", "Firebase returned ${list.size}, lastUpdated: $lastUpdated")
            // 3. Insert new record to ROOM
            executor.execute {
                var time = lastUpdated
                for (generalPost in list) {
                    database.GeneralPostDao().insert(generalPost)

                    generalPost.lastUpdated?.let {
                        if (time < it)
                            time = generalPost.lastUpdated ?: System.currentTimeMillis()
                    }
                }

                // 4. Update local data
                GeneralPost.lastUpdated = time
                generalPostsListLoadingState.postValue(LoadingState.LOADED)
            }
        }
    }

    fun addGeneralPost(generalPost: GeneralPost, callback: () -> Unit) {
        FirebaseGeneralPostModel.addGeneralPost(generalPost) {
            FirebasePersonPostModel()
            callback()
        }
    }
    fun getGeneralPostById(id: String, callback: (GeneralPost?) -> Unit) : LiveData<GeneralPost>{
        FirebaseGeneralPostModel.getGeneralPostById(id) {
            callback(it)
        }
        return database.GeneralPostDao().getGeneralPostById(id)
    }

    fun updateGeneralPost(generalPost: GeneralPost, callback: () -> Unit) {
            FirebaseGeneralPostModel.updateGeneralPost(generalPost) {
                executor2.execute {
                    database.GeneralPostDao().updateGeneralPost(generalPost)
                }
                //refreshAllgeneralPosts()
                callback()
            }
    }

    fun deleteGeneralPost(generalpost: GeneralPost, callback: () -> Unit) {
        FirebaseGeneralPostModel.deleteGeneralPost(generalpost.postid) {
            executor3.execute {
                database.GeneralPostDao().delete(generalpost)
            }
            callback()
        }
    }
}
