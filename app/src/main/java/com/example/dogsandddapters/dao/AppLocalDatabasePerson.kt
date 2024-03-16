package com.example.dogsandddapters.dao


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dogsandddapters.Models.Person
import com.example.dogsandddapters.base.MyApplication

@Database(entities = [Person::class], version = 5)
abstract class AppLocalDbPRepository : RoomDatabase() {
    abstract fun PersonDao(): PersonDao
}

object AppLocalDatabasePerson {
    val db: AppLocalDbPRepository by lazy {

        val context = MyApplication.Globals.appContext
            ?: throw IllegalStateException("Application context not available")

        Room.databaseBuilder(
            context,
            AppLocalDbPRepository::class.java,
            "PersonFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

