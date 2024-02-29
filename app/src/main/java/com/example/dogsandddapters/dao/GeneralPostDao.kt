package com.example.dogsandddapters.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dogsandddapters.Models.GeneralPost

@Dao
interface GeneralPostDao {

    @Query("SELECT * FROM GeneralPost")
    fun getAll(): List<GeneralPost>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg feeds: GeneralPost)

    @Delete
    fun delete(generalpost: GeneralPost)

    @Query("SELECT * FROM GeneralPost WHERE postid =:id")
    fun getGeneralPostById(id: String): GeneralPost
}