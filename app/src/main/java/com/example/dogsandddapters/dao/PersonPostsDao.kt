package com.example.dogsandddapters.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dogsandddapters.Models.PersonPost
@Dao
interface PersonPostsDao {

        @Query("SELECT * FROM PersonPost")
        fun getAll(): List<PersonPost>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(vararg personpost: PersonPost)

        @Delete
        fun delete(personpost: PersonPost)

        @Query("SELECT * FROM PersonPost WHERE id =:id")
        fun getPersonPostById(id: String): PersonPost

}