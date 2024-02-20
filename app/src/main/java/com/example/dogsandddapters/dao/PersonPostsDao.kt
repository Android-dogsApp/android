package com.example.dogsandddapters.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dogsandddapters.Models.PersonPost
@Dao
interface PersonPostsDao {

        @Query("SELECT * FROM PersonPost")
        fun getAll(): LiveData<MutableList<PersonPost>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(vararg personpost: PersonPost)

        @Delete
        fun delete(personpost: PersonPost)

        @Query("SELECT * FROM PersonPost WHERE id =:id")
        fun getPersonPostById(id: String): PersonPost

}