package com.example.dogsandddapters.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dogsandddapters.Models.Person
@Dao
interface PersonDao {

//    @Query("SELECT * FROM Person")
//    //fun getAll(): List<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg person: Person)

    @Delete
    fun delete(person: Person)

    @Query("SELECT * FROM Person WHERE id =:id")
    fun getPersonById(id: String): LiveData<Person>

    @Update
    fun update(person: Person)
}