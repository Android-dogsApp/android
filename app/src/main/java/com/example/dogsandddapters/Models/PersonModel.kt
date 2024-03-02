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
    private val personLiveData: LiveData<Person>? = null
    private val personLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)

    companion object {
        val instance: PersonModel = PersonModel()
    }

    fun getPerson(id: String, callback: (Person?) -> Unit) : LiveData<Person>{
        refreshPerson(id){
            callback(it)
        }
        return personLiveData?: database.PersonDao().getPersonById(id)
    }

//    fun getPerson(id: String, callback: () -> Unit) {
//        val person = firebasePersonModel.getPerson(id){
//            callback()
//        }
//        return person;
//    }
    private fun refreshPerson(id: String, callback: (Person?) -> Unit) {
        personLoadingState.value = LoadingState.LOADING
        val lastUpdated: Long = Person.lastUpdated
        firebasePersonModel.getPerson(lastUpdated, id) { person ->
            callback(person) // fills the profile fields with the person's data
            executor.execute {
                var time = lastUpdated
                if (person != null) {
                    database.PersonDao().insert(person)
                }
                person?.lastUpdated?.let {
                        if (time < it)
                            time = person.lastUpdated ?: System.currentTimeMillis()
                }
                Person.lastUpdated = time
                personLoadingState.postValue(LoadingState.LOADED)
            }
        }
    }


    fun addPerson(person: Person, callback: () -> Unit) {
        firebasePersonModel.addPerson(person) {
            refreshPerson(person.id) {
                callback()
            }
            callback()
        }
    }

    fun updatePerson( person: Person, callback: () -> Unit) {
        firebasePersonModel.updatePerson(person) {
            database.PersonDao().update(person)
            refreshPerson(person.id) {
                callback()
            }
        }
    }

}