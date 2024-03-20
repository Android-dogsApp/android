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
     val personLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)

    companion object {
        val instance: PersonModel = PersonModel()
    }

    fun getPerson(id: String, callback: (Person?) -> Unit) {
        firebasePersonModel.getPerson(id) {
            callback(it)
        }
    }



    fun getPersonByEmail(email: String, callback: (Person?) -> Unit): LiveData<Person> {
        firebasePersonModel.getPersonByEmail(email) {
            callback(it)

        }
        personLiveData = database.PersonDao().getPersonByemail(email)
        return personLiveData!!
    }



        fun updatePerson(id:String,person: Person, callback: () -> Unit) {
            firebasePersonModel.updatePerson(id,person) {
                executor.execute {
                    database.PersonDao().update(person)
                    //refreshPerson(person.id) {
                    callback()
                    //}
                }
            }
        }

}