package com.example.dogsandddapters.dao


import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dogsandddapters.base.MyApplication

//@Database(entities = [Student::class], version = 3)
abstract class AppLocalDbPRepository : RoomDatabase() {
    abstract fun PersonPostsDao(): PersonPostsDao
}

object AppLocalDatabasePerson {
    val db: AppLocalDbPRepository by lazy {

        val context = MyApplication.Globals.appContext
            ?: throw IllegalStateException("Application context not available")

        Room.databaseBuilder(
            context,
            AppLocalDbPRepository::class.java,
            "dbFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

