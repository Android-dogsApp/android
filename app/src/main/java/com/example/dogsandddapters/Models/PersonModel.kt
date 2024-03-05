package com.example.dogsandddapters.Models

import android.os.Looper
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dogsandddapters.dao.AppLocalDatabasePerson
import java.util.concurrent.Executors


class PersonModel private constructor() {
    enum class LoadingState {
        LOADING,
        LOADED
    }

    private val database = AppLocalDatabasePerson.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebasePersonModel = FirebasePersonModel()
    private var personLiveData: LiveData<Person>? = null
    private val personLoadingState: MutableLiveData<LoadingState> =
        MutableLiveData(LoadingState.LOADED)

    companion object {
        val instance: PersonModel = PersonModel()
    }

    fun getPerson(id: String, callback: (Person?) -> Unit) {
        firebasePersonModel.getPerson(id) {
            callback(it)
        }
    }

//    fun getPersonByEmail(email: String, callback: (Person?) -> Unit) {
//        database.PersonDao().getPersonByEmail(email).observeForever { person ->
//            if (person != null) {
//                // If person found in local database
//                callback(person)
//            } else {
//                // If person not found in local database, check Firebase
//                firebasePersonModel.getPersonByEmail(email) { user ->
//                    if (user != null) {
//                        // If user found in Firebase, insert into local database
//                        database.PersonDao().insert(user)
//                        callback(user)
//                    } else {
//                        // User not found in local database or Firebase
//                        callback(null)
//                    }
//                }
//            }
//        }
//    }


    fun getPersonByEmail(email: String, callback: (Person?) -> Unit) {
        firebasePersonModel.getPersonByEmail(email) {
            callback(it)

        }
    }


//    fun getPerson(id: String, callback: (Person?) -> Unit) : LiveData<Person>{
//        personLiveData =database.PersonDao().getPersonById(id)
//        refreshPerson(id){
//            callback(it)
//        }
//        return personLiveData!!
//    }

//    fun getPerson(id: String, callback: () -> Unit) {
//        val person = firebasePersonModel.getPerson(id){
//            callback()
//        }
//        return person;
//    }

//    private fun refreshPerson(id: String, callback: (Person?) -> Unit) {
//        personLoadingState.value = LoadingState.LOADING
//        val lastUpdated: Long = Person.lastUpdated
//        firebasePersonModel.getPerson(lastUpdated, id) { person ->
//            callback(person) // fills the profile fields with the person's data
//            executor.execute {
//                var time = lastUpdated
//                if (person != null) {
//                    database.PersonDao().insert(person)
//                }
//                person?.lastUpdated?.let {
//                        if (time < it)
//                            time = person.lastUpdated ?: System.currentTimeMillis()
//                }
//                Person.lastUpdated = time
//                personLoadingState.postValue(LoadingState.LOADED)
//            }
//        }
//    }


        fun addPerson(person: Person, callback: () -> Unit) {
            firebasePersonModel.addPerson(person) {
                //refreshPerson(person.id) {
                callback()
                //}
            }
        }

        fun updatePerson(person: Person, callback: () -> Unit) {
            firebasePersonModel.updatePerson(person) {
                database.PersonDao().update(person)
                //refreshPerson(person.id) {
                callback()
                //}
            }
        }

}