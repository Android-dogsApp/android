package com.example.dogsandddapters.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dogsandddapters.Models.Person
interface PersonDao {

    @Query("SELECT * FROM Person")
    fun getAll(): List<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg personpost: Person)

    @Delete
    fun delete(personpost: Person)

    @Query("SELECT * FROM PersonPost WHERE id =:id")
    fun getPersonPostById(id: String): Person
}