package com.example.dogsandddapters.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.base.MyApplication

 @Database(entities = [PersonPost::class], version = 5)
    abstract class AppLocalDbPPRepository : RoomDatabase() {
        abstract fun PersonPostsDao(): PersonPostsDao
    }

    object AppLocalDatabasePersonPost {
         val db: AppLocalDbPPRepository by lazy {

            val context = MyApplication.Globals.appContext
                ?: throw IllegalStateException("Application context not available")

            Room.databaseBuilder(
                context,
                AppLocalDbPPRepository::class.java,
                "PersonPostFileName.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

