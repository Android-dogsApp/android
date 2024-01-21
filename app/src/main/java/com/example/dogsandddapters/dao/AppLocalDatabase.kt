package com.example.dogsandddapters.dao

import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dogsandddapters.Models.Person
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.base.MyApplication

object AppLocalDatabase {
    abstract class AppLocalDbRepository : RoomDatabase() {
        abstract fun studentDao(): PersonPostsDao
    }

    object AppLocalDatabase {
         val db: AppLocalDbRepository by lazy {

            val context = MyApplication.Globals.appContext
                ?: throw IllegalStateException("Application context not available")

            Room.databaseBuilder(
                context,
                AppLocalDbRepository::class.java,
                "dbFileName.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}