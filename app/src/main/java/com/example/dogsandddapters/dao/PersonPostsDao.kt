package com.example.dogsandddapters.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dogsandddapters.Models.PersonPost
@Dao
interface PersonPostsDao {

        @Query("SELECT * FROM PersonPost")
        suspend fun getAll(): LiveData<MutableList<PersonPost>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(vararg personpost: PersonPost)

        @Delete
        suspend fun delete(personpost: PersonPost)

        @Query("SELECT * FROM PersonPost WHERE postid =:id")
        suspend fun getPersonPostById(id: String): LiveData<PersonPost>

        //@Query("SELECT * FROM PersonPost WHERE postid =:id")
        @Update
        suspend fun updatePersonPost(personpost: PersonPost)



}