package com.example.dogsandddapters.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dogsandddapters.Models.GeneralPost

@Dao
interface GeneralPostDao {


    @Query("SELECT * FROM GeneralPost")
     fun getAll(): LiveData<MutableList<GeneralPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(vararg feeds: GeneralPost)

    @Delete
     fun delete(generalpost: GeneralPost)

    @Query("SELECT * FROM GeneralPost WHERE postid =:id")
     fun getGeneralPostById(id: String):  LiveData<GeneralPost>

    //@Query("SELECT * FROM GeneralPost WHERE postid =:id")
    @Update
     fun updateGeneralPost(generalpost: GeneralPost)


}