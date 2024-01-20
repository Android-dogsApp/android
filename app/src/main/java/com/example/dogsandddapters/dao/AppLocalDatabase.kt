package com.example.dogsandddapters.dao

import androidx.room.RoomDatabase
object AppLocalDatabase {
    abstract class AppLocalDbRepository : RoomDatabase() {
        abstract fun studentDao(): PersonPostsDao
    }

    object AppLocalDatabase {
        // val db: AppLocalDbRepository by lazy {
//
//            val context = MyApplication.Globals.appContext
//                ?: throw IllegalStateException("Application context not available")
//
//            Room.databaseBuilder(
//                context,
//                AppLocalDbRepository::class.java,
//                "dbFileName.db"
//            )
//                .fallbackToDestructiveMigration()
//                .build()
//        }
    }

}